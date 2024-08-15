package com.bns.hstcld.functions.cloud_event;

import com.google.cloud.functions.CloudEventsFunction;
import com.google.events.cloud.pubsub.v1.MessagePublishedData;
import com.google.gson.Gson;
import com.google.protobuf.util.JsonFormat;
import io.cloudevents.CloudEvent;
import io.cloudevents.CloudEventData;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class PubsubCloudEventFunction implements CloudEventsFunction {

    private static final Logger logger = Logger.getLogger(PubsubCloudEventFunction.class.getName());

    @Override
    public void accept(CloudEvent event) throws Exception {
        if (event != null) {
            // Extract JSON from CloudEvent
            String json = new String(event.getData().toBytes(), StandardCharsets.UTF_8);
            // Convert JSON to Pubsub
            MessagePublishedData.Builder builder = MessagePublishedData.newBuilder();
            JsonFormat.parser().merge(json, builder);
            MessagePublishedData data = builder.build();
            logger.info("### CloudEvent Data ###");
            logger.info(data.getSubscription());
            logger.info(data.getMessage().getMessageId());
            logger.info(data.getMessage().getData().toString());

//            Gson gson = new Gson();
//            PubSubBody body = gson.fromJson(json, PubSubBody.class);


        }
    }
}
