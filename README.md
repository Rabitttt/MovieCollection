# MovieCollection
Movie Collection to store your movies and show your movies to other people.

# Usage

##### Before Start Make Sure **Git,Maven and Docker** Installed
* [Docker](https://docs.docker.com/engine/install/)
* [Maven](https://maven.apache.org/index.html) 
* [Git](https://git-scm.com/downloads)

#### Download
> Clone This Project (Make Sure You Have Git Installed)
```
https://github.com/Rabitttt/MovieCollection.git
```

#### Dependencies
> Install Dependencies (Make Sure You Have Maven Installed) 
```
maven install
```

#### Database

> Up Database via Docker (Make Sure you are in directory same as resources/docker-compose.yml)
```
sudo docker-compose up -d 
```
##### Connect Database (Not Nesseccary)
> To Learn CONTAINER_ID
```
sudo docker ps -a
```

> Connect Database with postgres User 
`
cd /MovieCollection/src/main/resources/
`
```
sudo docker exec -it [ POSTGRES_IMAGE_CONTAINER_ID ] psql -U postgres
```
```
\c moviecollection
```
You can type any psql command here.

#### RUN
> After all these steps , you can start testing and developing this project.
> Basicly you can run via any IDE or you can run in terminal via below command.
`
cd /MovieCollection/
`
```
mvn spring-boot:run
```
After all these steps , you can start testing and developing this project.

#### Note
> This project dont have any default admin. To test admin role, you can basicly sign up with 'admin' username and you are going to be 

### Contact
If you have any confused about project , dont hesitate to ask me your question.
<burakkaragol60@gmail.com> Here is my mail.

### Lisence
This Project Have __GNU General Public License v3.0__  Which is Open Source Lisence. 

#### That's it! Happy Coding!
