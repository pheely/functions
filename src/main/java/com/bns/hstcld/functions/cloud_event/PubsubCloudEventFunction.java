package com.bns.hstcld.functions.cloud_event;

import com.google.cloud.functions.CloudEventsFunction;
import com.google.gson.Gson;
import io.cloudevents.CloudEvent;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

public class PubsubCloudEventFunction implements CloudEventsFunction {

    private static final Logger logger = Logger.getLogger(PubsubCloudEventFunction.class.getName());

    @Override
    public void accept(CloudEvent event) throws Exception {
        if (event != null) {
            // Extract JSON from CloudEvent
            String json = new String(event.getData().toBytes(), StandardCharsets.UTF_8);
            logger.info(json);

            // Convert JSON to MessagePublishedData that is defined as a protobuf
            // Unfortunately, this does not work. See this for more details.
            // https://stackoverflow.com/questions/68490891/google-cloud-platform-push-subscription-sending-duplicate-message-id-fields
            //
            // A better option is to define a POJO myself.
//            MessagePublishedData.Builder builder = MessagePublishedData.newBuilder();
//            JsonFormat.parser().merge(json, builder);
//            MessagePublishedData data = builder.build();
//            logger.info("### CloudEvent Data ###");
//            logger.info(data.getSubscription());
//            logger.info(data.getMessage().getMessageId());
//            logger.info(data.getMessage().getData().toString());

            Gson gson = new Gson();
            PubSubBody body = gson.fromJson(json, PubSubBody.class);
            logger.info("### CloudEvent Data ###");
            logger.info("Subscription: " + body.getSubscription());
            logger.info("MessageId: " + body.getMessage().getMessageId());
            logger.info("Payload: " + new String(Base64.getDecoder().decode(body.getMessage().getData())));
            logger.info("PublishTime: " + body.getMessage().getPublishTime().toString());
        }
    }
}

