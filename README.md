# Cloud Functions

## Hello World

### Coding

All you need is a class that implements `HttpFunction`.

### Local Testing

For local test, use [Google Functions Framework](https://github.com/GoogleCloudPlatform/functions-framework-java)
```bash
./gradlew runFunction -Prun.functionTarget=com.bns.hstcld.functions.http.HelloWorld
```

### Deployment

1. To deploy, use the `gcloud` command:
   ```bash
   gcloud services enable cloudfunctions.googleapis.com
   gcloud services enable run.googleapis.com
   gcloud services enable artifactregistry.googleapis.com
   gcloud functions deploy hello-world \
   --gen2 \
   --runtime=java17 \
   --region=us-east4 \
   --source=. \
   --entry-point=com.bns.hstcld.functions.http.HelloWorld \
   --memory=512MB \
   --trigger-http
   ```
2. List the deployed cloud functions and get the endpoint
    ```bash
    gcloud run services list
    ```
3. Test GET
    ```bash
    URL=$(gcloud functions  describe hello-world --region=us-east4 --format="value(url)")
    curl "$URL/?name=jenny" \
    -H "Authorization: Bearer $(gcloud auth print-identity-token)" \
    -w '\n'
    ```
4. Test POST
   ```bash
   URL=$(gcloud functions  describe hello-world --region=us-east4 --format="value(url)")
   curl  $URL \6426
   -H 'Content-Type: application/json' \
   -H "Authorization: Bearer $(gcloud auth print-identity-token)" \
   -d "{"name": "Jie"}" \
   -w '\n'
   ```
5. Delete the function
   ```bash
   gcloud functions delete hello-world --region us-east4
   ```

## Cloud Event Driven Functions

`CloudEventsFunction` can be triggered when a cloud event is fired. We need 
to define a cloud event handler, a class that implements the 
`CloudEventsFunction` interface.

There is only one method `accept` defined in this interface. The method 
takes one argument of type of `CloudEvent`. The payload of a `CloudEvent` 
varies with the event type that triggers the function. It is a JSON object 
that can be converted into a Protobuf or a POJO. 

### Local Testing

Start the function:
```bash
./gradlew runFunction \
-Prun.functionTarget=com.bns.hstcld.functions.cloud_event.PubsubCloudEventFunction
```

Send a sample event to the function:
```bash
curl -v localhost:8080 \
-H "Content-Type: application/json" \
-H "ce-id: 123451234512345" \
-H "ce-specversion: 1.0" \
-H "ce-time: 2020-01-02T12:34:56.789Z"  \
-H "ce-type: google.cloud.pubsub.topic.v1.messagePublished" \
-H "ce-source: //pubsub.googleapis.com/projects/MY-PROJECT/topics/MY-TOPIC" \
-d '{
  "message": {
    "data": "SGkgSmll",
    "messageId": "11983746854422142",
    "message_id": "11983746854422142",
    "publishTime": "2024-08-15T21:47:31.311Z",
    "publish_time": "2024-08-15T21:47:31.311Z"
  },
  "subscription": "projects/ibcwe-event-layer-f3ccf6d9/subscriptions/eventarc-us-east4-pubsub-function-064067-sub-259"
}'
```
### Deployment

To create a Pubsub topic:
```bash
gcloud pubsub topics create cloud-function-topic
```

To deploy the function:
```bash
gcloud functions deploy pubsub-function \
--gen2 \
--runtime=java17 \ 
--region=us-east4 \
--source=. \
--entry-point=com.bns.hstcld.functions.cloud_event.PubsubCloudEventFunction \
--memory=512MB \
--trigger-topic=cloud-function-topic
```

To trigger the function:
```bash
gcloud pubsub topics publish cloud-function-topic --message="Hi Jie"
```

To check the log:
```bash
gcloud functions logs read \
--gen2 \
--region=us-east4 \
--limit=15 
pubsub-function
```

To delete the function:
```bash
gcloud functions delete pubsub-function --region us-east4
```

To delete the pubsub topic:
```bash
gcloud pubsub topics delete cloud-function-topic
```

## Spring Cloud Function Framework

Version: 4.2.0-SNAPSHOT

Modules:
- org.springframework.cloud:spring-cloud-function-adapter-gcp:4.2.0-SNAPSHOT
- org.springframework.cloud:spring-cloud-function-context


