# IOT-Android-Things-Project-Backend
iot/spring project for UltraSonic Sensor Andoid Things Project

The middleware between application and Firebase that will be deployed to your Application Server. This project contains the logic that monitors when a new motion log is added on the server. It constructs a push notification to send to registered mobile devices. Also stores the image.
 
The Spring Boot project requires Maven and Redis, which you can install by following the instructions on https://redis.io/topics/quickstart Installing Redis also installs Maven.

Once you have Maven and Redis installed, configure your Backend by changing Authorization key which taken from Firebase and than simply deploy the any server / container.  

You must configure properties which are the following lines.
```
spring.redis.host=yourRemoteHost
spring.redis.password=yourPasswordIfExist
spring.redis.port=yourRemotePort
spring.redis.database=yourDBIndex
image.url.prefix=yourImageServerAddress
notification.title=New notification from Device
```

You should then be able to send notification in your backend whenever you're ready. Default environments & configurations set up for Glassfish Server.

- There is a postman collection for make it easy : ) 

## Sample App

<p><a href="https://things.mbakgun.com/IOT/registerThings.json">Visit our sample application</a></p>


## Contact me
Any questions:Please feel free to contribute by pull request, issues or feature requests.
* Email: burak@mbakgun.com
* Linkedin: https://www.linkedin.com/in/mbakgun/

## License

    Copyright 2018 Mehmet Burak Akgün

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
