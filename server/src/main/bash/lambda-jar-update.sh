#!/bin/bash
arr=(
        "getFollowing"
        "getFeed"
        "login"
    )
for FUNCTION_NAME in "${arr[@]}"
do
  aws lambda update-function-code --function-name $FUNCTION_NAME --zip-file fileb://D:/Workspace/Java/CS_340/Tweeter-Backend/out/artifacts/backend_server_main_jar/backend.server.main.jar &
done