package it.cuncurrency;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Consumer implements Runnable {

    // Initialize the logger for the class
    private static final Logger logger = LogManager.getLogger(Consumer.class);

    ConcurrentLinkedQueue<String> queue;

    Consumer(ConcurrentLinkedQueue<String> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        String imgSrc;

        // It will always wait for another element in the queue
        // TODO Maybe could be used a notifyAll to end all threads
        while (true) {

        	// If there aren't elements to process then wait
        	if ((imgSrc = queue.poll()) == null) {
        		try {
    				Thread.sleep(500);
    				continue;
    			} catch (InterruptedException e) {
    				logger.error(e.toString());
    			}
        	}

        	// There is one element to process
			logger.info("Removed: " + imgSrc);

			Image image = null;

			URL url;
			try {
				url = new URL(imgSrc);
				image = ImageIO.read(url);

				// From url take filename and then extension
				String filename = imgSrc.substring(imgSrc.lastIndexOf("/") + 1);
				String extension = filename
						.substring(filename.indexOf(".") + 1);

				File outputfile = new File(filename);
				ImageIO.write((RenderedImage) image, extension, outputfile);
			} catch (IOException e) {
				logger.error(e.toString());
			}

        }
    }
}
