# CS 2112 A7: Demo Client and Server
For Assignment 7 you will need to choose a library to use for communicating between a server and a client.  In this repository, we have chosen to show you how to use a library called **Spark**, but you are welcome to choose a different library.

**However**, unlike previous iterations of this course, **you are required to use a build system for your project**.  In this repository we have chosen to use **Gradle** as it is generally easier to get working. You **must** use Gradle. We will provide you with a repository that has Gradle set up with Spark. You _may_ use a different server framework, but you will be responsible for integrating it with the Gradle setup yourself.

1. Decide what java server framework you want to use.
    - Hint: we strongly endorse **Spark**.  Investigate alternatives and you will find that it is quite elegant.
2. Get your build system working.
3. Do this early.

This repository is provided to you as is to enable you to get started, you are welcome to use any and all of the code (and build scripts) provided herein.

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
# IF YOU READ ANYTHING READ THIS
We have put a lot of effort to make the setup of a7 as easy as possible so you can get coding!  This demo repository
is presented in the order you should execute actions.

1. First, get this repository working and make sure you can launch the demo server and client using Spark / Gradle.
2. Follow the [directions at the end of the document](#converting) to turn get your new 2112 repo working the same.

------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------

## Overview
1. Example web server framework: [Spark](#spark)
2. Example build system: [Gradle](#gradle)
3. How to run the server: [Demo Server](#demo-server)
4. How to run the client: [Demo Client](#demo-client)
5. Getting your code working the same: [Converting](#converting)

## Spark
This demo server uses a library called [Spark](http://sparkjava.com/). To see how to use Spark, see [src/main/java/Server.java](src/main/java/Server.java). It contains examples with lots of comments explaining how they work.

### Using Spark
All the main Spark functions are `static` functions in the class `spark.Spark`. You can see the API for that class [here](http://spark.screenisland.com/spark/Spark.html). The first function you will want to call is `port(int port)`. This specifies the port the server will run on. For example, this code would make your server run on port 8080:
```java
Spark.port(8080)
```

Another important function is `get(String path, Route route)`. This function binds a handler to a specific route on your path on your server. For example, the following code creates a handler which responds to GET requests at `/hello` with the string `Hello World`:
```java
get("/hello", new Route() {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return "Hello World";
    }
});
```
While this works, it is not very elegant. There are more lines that are there for syntactical reasons than lines that actually do anything interesting. To fix this, Java 8 introduced [lambda expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html). The following code is equivalent and much easier to read and write:
```java
get("/hello", (request, response) -> "Hello World");
```

For more examples, including post requests and using GSON to convert to and from JSON, see [src/main/java/Server.java](src/main/java/Server.java).

## Gradle
This demo uses a build system called Gradle, which manages all the dependencies. This project uses [GSON](https://github.com/google/gson), a library for working with Java Script Object Notation (JSON). It also uses [Spark](http://sparkjava.com/). The gradle build file (`build.gradle`) lists these as dependencies:
```groovy
// The spark webserver
compile 'com.sparkjava:spark-core:2.3'
// GSON, for parsing and generating JSON
compile group: 'com.google.code.gson', name: 'gson', version: '2.4'
```
When you build the project (using Eclipse or by running `gradle build` on the command line), Gradle will download all the required jars for you automatically. This makes it easy to get the project working on any machine without having to worry about downloading jars. Most importantly for you, it means that your grader for A7 will be able to easily compile your project without having to worry about finding any dependencies you use, since as long as you list them in `build.gradle`, Gradle will download them automatically.

### Setup
Eclipse has a nice Gradle plugin called [Buildship](https://projects.eclipse.org/projects/tools.buildship).  If you have an even remotely recent version of Eclipse, **this should already be installed**.  To verify it is, when Eclipse is open choose `File -> Import...`, there should be a `Gradle` folder and it will look something like this:

<p align="center">
    <img src="https://github.coecis.cornell.edu/cs2112-fa16/a7-demo/blob/master/haz_gradle.jpg" alt="Gradle import in Eclipse">
</p>

If it's not installed, follow the [directions provided by Gradle](https://github.com/eclipse/buildship/blob/master/docs/user/Installation.md).

Once you have the plugin installed, you can import this Gradle project. Clone this repo (or just download a ZIP), and then go to `File -> Import -> Gradle -> Gradle Project`, and step through the menu. If you get a strange error message saying something like "EclipseProperty" not found, there is something strange about the version of Gradle bundled with your Eclipse installation. To fix this, download the binary distribution of Gradle from https://gradle.org/gradle-download/ and unzip it. Then, try importing your project again and on the screen that lets you choose which Gradle distribution to use, pick the folder you just unzipped.

You should be able to run this project just like any other Eclipse project now.

### Having Trouble?
We have prepared a video for you to see the process start to finish if you need help, or would rather follow along.  Click the image to view the video.

<p align="center">
    <a href="https://youtu.be/DoxngL85Kvc" target="_blank"><img src="https://github.coecis.cornell.edu/cs2112-fa16/a7-demo/blob/master/video_image.jpg" alt="a7 preview"></a>
</p>

## Demo Server
Before a client can connect, you need to start the server:
```
$ java -jar build/libs/a7-demo.jar server
```
The server will open up on `localhost` using port `8080` in this program, and serves the following content:

* GET http://localhost:8080/hello
* GET http://localhost:8080/test
* POST http://localhost:8080/post
* GET http://localhost:8080/message
* POST http://localhost:8080/message

Try playing with each one! You can use [Postman](https://www.getpostman.com/), which is a nice GUI HTTP client. You can also use CURL, a command line tool. (Note that using CURL is not required. You can simply use Postman, or any other tool of your choosing. If you are using a Windows computer, I'm not sure if it has CURL or how you would use it.) For example, try it on `/hello`:
```
$ curl http://localhost:8080/hello
```

You can also send post requests with data using CURL:
```
$ curl --data "foo=bar&fun=cow" http://localhost:8080/post
```

You can send data in the body of a post request too:
```
$ curl -H "Content-Type: application/json" -X POST -d '{"message":"you figured out how to change me"}' http://localhost:8080/message
```

And you can read back you changed message:
```
$ curl http://localhost:8080/message
```

## Demo Client

To run the client, do:
```
$ java -jar build/libs/a7-demo.jar client http://localhost:8080/message "My New Message"
```
To read the message, do:
```
$ java -jar build/libs/a7-demo.jar client http://localhost:8080/message
```

The demo client only works with the `/message` endpoint. Using it with any other endpoint will probably cause it to crash.

## Converting
In order to use Gradle, Spark, GSON, and any other libraries you might use in your final project, you will need to convert your project to a Gradle project. Follow these steps carefully:

1. Clone your new repo. Its name will be of the form `team-jng55-sjm324-final`.
2. The cloned copy should have the following structure:

  ```
  └── team-jng55-sjm324-final
    ├── build.gradle
    ├── format.sh
    ├── gradlew
    ├── gradlew.bat
    ├── README.md
    ├── settings.gradle
    └── src
        ├── main
        │   ├── java
        │   │   └── README.md
        │   └── resources
        │       └── README.md
        └── test
            ├── java
            │   └── README.md
            └── resources
                └── README.md

  ```
3. Copy the contents of your _original_ project's `src` folder to the `src/main/java` folder inside the _new_ repo. For example, if you had the class `Foo` in the package `bar` in your original project, it would be at `src/bar/Foo.java`. Copy that file such that it is at `src/main/java/bar/Foo.java` in your new repo.
4. Move all of your tests to the `src/test/java` folder, maintaining the package structure just like you did for the source.
5. If your code or tests depended on any extra files (like a `constants.txt` or a `test_world.txt`) move the files that your code depends on to `src/main/resources`. For example, if your code needs to read a `constants.txt` file, copy the file to `src/main/resources/constants.txt`. To read the file in your code, do:
  
  ```java
  InputStream file = getClass().getResourceAsStream("/constants.txt");
  ```
 Do the same thing for any files you need to use in your tests, except put them in `src/test/resources`.
6. Edit the `settings.gradle` file to give your project a good name. For example, `rootProject.name = 'hexworld'`.
7. Open Eclipse. Import the project as a Gradle project as discussed above.

Now that your project is a Gradle project, you are all ready to go!
