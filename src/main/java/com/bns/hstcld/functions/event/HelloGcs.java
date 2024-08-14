package com.bns.hstcld.functions.event;

import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;

import java.util.logging.Logger;

public class HelloGcs implements BackgroundFunction<GcsEvent> {
    private static final Logger logger = Logger.getLogger(HelloGcs.class.getName());

    @Override
    public void accept(GcsEvent event, Context context) throws Exception {
        logger.info("Event: " + context.eventId());
        logger.info("Event Type: " + context.eventType());
        logger.info("Bucket: " + event.getBucket());
        logger.info("File: " + event.getName());
        logger.info("Metageneration: " + event.getMetageneration());
        logger.info("Created: " + event.getTimeCreated());
        logger.info("Updated: " + event.getUpdated());
    }
}
