cd rabbit-server
kubectl apply -f rabbit-deployment.yaml
kubectl apply -f rabbit-service.yaml

cd ../discovery-server
./gradlew clean build
./gradlew buildDockerImage
./gradlew pushDockerImage
kubectl apply -f discovery-deployment.yaml
kubectl apply -f discovery-service.yaml

cd ../auth
./gradlew clean build
./gradlew buildDockerImage
./gradlew pushDockerImage
kubectl apply -f auth-deployment.yaml
kubectl apply -f auth-service.yaml

cd ../projects
./gradlew clean build
./gradlew buildDockerImage
./gradlew pushDockerImage
kubectl apply -f projects-deployment.yaml
kubectl apply -f projects-service.yaml

cd ../api-gateway
./gradlew clean build
./gradlew buildDockerImage
./gradlew pushDockerImage
kubectl apply -f gateway-deployment.yaml
kubectl apply -f gateway-service.yaml
kubectl apply -f gateway-ingress.yaml

#keep this commented
#cd ../keycloak-server
#./gradlew clean build
#./gradlew buildDockerImage
#./gradlew pushDockerImage
#kubectl apply -f keycloak-deployment.yaml
#kubectl apply -f keycloak-service.yaml