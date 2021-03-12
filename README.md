# MovieCollection
Movie Collection helps you to collecting your movies and show them to other people.

# Usage

##### Please make sure you have these gadgets installed before using:
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
sudo docker exec -it [POSTGRES_IMAGE_CONTAINER_ID] psql -U postgres
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
> This project don't have any default admin. To test the admin role, you can sign up with 'admin' username and you are going to be admin.

### Contact
If you have any confused about project , don't hesitate to ask me your question.
<burakkaragol60@gmail.com> Here is my mail.

### License
This Project Have __GNU General Public License v3.0__  Which is Open Source License. 

#### That's it! Happy Coding!
