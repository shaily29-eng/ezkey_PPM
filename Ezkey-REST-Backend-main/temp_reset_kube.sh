kubectl delete deployments/api-gateway
kubectl delete deployments/projects
kubectl delete deployments/auth
kubectl delete deployments/discovery-server
kubectl delete statefulset/kafka
kubectl delete statefulset/zookeeper
#kubectl delete deployments/keycloak

#kubectl delete services/gateway-service
#kubectl delete services/projects-service
#kubectl delete services/auth-service
#kubectl delete services/discovery-service
#kubectl delete deployments/keycloak-service