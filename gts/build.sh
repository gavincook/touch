mvn package
cp ./lib/txc-client-2.0.72.jar client/lib/
cd client/bin
sed 's/sample-txc-simple-1.0-SNAPSHOT.jar/sample-txc-simple-1.0-SNAPSHOT.jar:"$REPO"\/txc-client-2.0.72.jar/' ./start.sh >run.sh
chmod +x run.sh