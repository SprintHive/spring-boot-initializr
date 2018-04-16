# {{prettyName}} Service

A description of the purpose of this project

## Running

    ./gradlew bootRun
    
    # run with debug
    ./gradlew bootRun --debug-jvm 
    
## Libraries 

[http://projects.spring.io/spring-boot/]()  
[https://projectlombok.org]() - Spice up your java: Automatic Resource Management, 
automatic generation of getters, setters, equals, hashCode and toString, and more!

## External Dependencies

* Any external dependencies

## Profiles

dev - properties are loaded from application.yaml 
test - properties are loaded from application-test.yaml   
preprod - properties are loaded from application-preprod.yaml  
production - properties are loaded from application-production.yaml

Helper scripts to run the app with different profiles

/bin/runTest.sh - packages the app and runs with the test profile activated
/bin/runPrepod.sh - packages the app and runs with the preprod profile activated
/bin/runProd.sh - packages the app and runs with the prod profile activated

### Override props

You can override props by creating a <project-home>/application.yaml this file is .gitignored

## Logging

@Slf4j
lombok
logback
fluent 

### When to use what level?
Error : A problem has occurred that will cause a server error to be returned to the client or is fatal for an operation
Warning : A problem has occurred that is non-fatal to the request or operation (e.g. a service call had to be retried)
Info : High level information that provides greater visibility into the operation of the application
Debug : Low level information that provides greater visibility into the operation of the application

## Testing

To run an individual test:

    $ ./gradlew test -Dtest.single=PropTest

To debug a test:

    $ ./gradlew test -Dtest.debug=true -Dtest.single=DemoSpec

## Deployment

    # create executable jar see <project-home>/bin/run*.sh for example 
    ./gradlew bootJar

## Deploying to Kubernetes 

The section describes how to deploy the starter app into kubernetes so that it can be accessed from outside of the cluster.

### Prerequisites

[minikube](https://github.com/kubernetes/minikube)  Minikube is a tool that makes it easy to run Kubernetes locally. Minikube 
runs a single-node Kubernetes cluster inside a VM on your laptop for users looking to try out Kubernetes or develop with it day-to-day.        
[kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl)  
[docker](https://www.docker.com)  


Create a executable jar file

    ./gradlew bootJar

Configure your docker to use your minikube's docker daemon.

    eval $(minikube docker-env)
    # to undo it run 
    eval $(minikube docker-env -u)

Create a docker image  

    docker build -t honeypot-kyc:v1 .
    
    # Confirm that it has been created
    docker images
    
    REPOSITORY                                             TAG                 IMAGE ID            CREATED             SIZE
    honeypot-kyc                                         v1                  e8efbf79ca06        10 seconds ago      223MB 
    
Create a deployment

There are 2 ways to create a deployment, using kubectl run or from a yaml file using kubectl create

Using kubectl run: 

    # This will create a pod and a deployment
    # Run uses a kubernetes default template to create a deployment
    kubectl run honeypot-kyc --image=honeypot-kyc:v1 --port=8080

From a yaml file:   

    # This will create a pod and a deployment
    kubectl create -f config/kubernetes/deployment-basic.yaml
    
Confirm that the pod and deployment was created

    # Confirm that the pod has been created 
    kubectl get po
    
    NAME                              READY     STATUS    RESTARTS   AGE
    honeypot-kyc-2028074326-pbzmm   1/1       Running   0          1m
    
    # Confirm that the deployment has been created 
    kubectl get deploy
        
    NAME             DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
    honeypot-kyc   1         1         1            0           1m

Test that you can connect to the pod by port forwarding to the pod.

    # Use kubectl to port forward to the pod
    kubectl port-forward honeypot-kyc-2028074326-pbzmm 8080:8080
    
    # To confirm that you can connect to the app browse to http://localhost:8080/ping 
    # You should see something like: 
    {"status":"UP"}
      
Create a service

Now to expose the app to the out side world we can create a service.  
There are 2 ways to create a service, using kubectl expose or from a yaml file 

Using kubectl expose

    kubectl expose deployment honeypot-kyc --type=NodePort

Using a yaml file

    kubectl create -f config/kubernetes/service-basic.yaml 
    
Confirm that the service was created    
    
    # List services
    kubectl get svc    

    NAME             CLUSTER-IP   EXTERNAL-IP   PORT(S)                     AGE
    ...
    honeypot-kyc   10.0.0.31    <nodes>     80:32314/TCP, 9080:32315/TCP   7s
    ...
    
    # Confirm that you can connect to the service via the node/node-port
    # Get the ip address of your minikube node
    minikube ip  
    > 192.168.99.100
        
    # Then using the node port connect to the service by browsing to
    http://192.168.99.100:32315/actuator/health

    # You should see something like: 
    {"status":"UP"}
    
Create a route so that you can connect to services using the external ip address and the service port.

> Warning: This has only applies to OSX, this route will have to be recreated when your machine is restarted 
> because your minikue ip may change.
> See this [blog post](https://stevesloka.com/2017/05/19/access-minikube-services-from-host/) for more info. 

    # Create a route between my local dev machine and minikube
    # Remove any existing routes
    sudo route -n delete 10/24 > /dev/null 2>&1
    
    # Create a route 
    sudo route -n add 10.0.0.0/24 $(minikube ip)

    # To confirm that you can connect to the app through the service
    # browse to http://10.0.0.31:9080/actuator/health
    # You should see something like: 
    {"status":"UP"}

## Ingress configuration

Assuming that you have installed [SprintHive SHIP](https://github.com/SprintHive/ship) then this next
step will get configure kong to route traffic to the spring starter app. 

    kubectl create -f config/kubernetes/ingress.yaml -n local
