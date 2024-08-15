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
   curl  $URL \
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

