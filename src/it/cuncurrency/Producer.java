package it.cuncurrency;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Producer implements Runnable {

    // Initialize the logger for the class
    private static final Logger logger = LogManager.getLogger(Producer.class);

    private ConcurrentLinkedQueue<String> queue;

    public Producer(ConcurrentLinkedQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        Document doc;

        try {
            // Connect throws IOException
            doc = Jsoup.connect("http://all-free-download.com/free-photos/cats.html").get();
            Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
            for (Element image : images) {
                String imgSrc = image.attr("src");
                queue.add(imgSrc);
                logger.info("src: " + imgSrc);
            }
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

}
