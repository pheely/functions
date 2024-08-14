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
   gcloud service artifactregistry.googleapis.com
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
    curl "$URL/?name=jenny" -w '\n'
    ```
4. Test POST
   ```bash
   URL=$(gcloud functions  describe hello-world --region=us-east4 --format="value(url)")
   curl  $URL \
   -H 'Content-Type: application/json' \
   -d "{"name": "Jie"}" \
   -w '\n'
   ```
5. Delete the function
   ```bash
   gcloud functions delete hello-world --region us-east4
   ```