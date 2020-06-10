
## 1. Introduction
## 1.1 Overview of Language
**IF-ELSE** focuses on Smartphone automation. We are targetting the Android devices as most of the people are using an Android based smartphones at this time. Native Android applications are mostly written in Java.
We will use _Java_ and _XML_ to develop the application.
* #### Java
    * Java will be used to deisgn the logic structure of the whole Android application.
    * There will be a global _Application_ class which contains various Android components like 
        1. Activities (Visible UI)
        2. Services (Background Tasks)
        3. Broadcast Receivers
        4. Content Providers
    * By writing the Java code, the app will interact with the various UI elements which are statically defined in **XML**.Java will         also make use of __JSON__ files for extending functionalities and for gathering data.  
* #### XML: eXtended Markup Language
    * XML will provide the static UI (User Interface) written in separate **_.xml_** files.
    * These designs will be stored as separate resources will be a part of the Android package.
Most of the implementation will be done using the core Android SDK, along with some additional open-source libraries for licensing. We will use the latest Android SDK available (currently **_API 28_**) for development, as it provides new features and more secure applications.

## 1.2 Specification
### Development
Our development worstation will have the following specification:
* #### Software Specification 
    * **IDE**: Android Studio v3.5
    * **Language**: Java, XML
    * **Platform**: Microsoft Windows 10
    * **Backend Database service**: Google Firebase
* #### Hardware Specification
    * **Processor**: Intel i5-8th Gen
    * **RAM**: 4GB or more
    * **Testing Equipment**: Android Smartphone
### End-User
The end-user must have an Android smartphone with following specifications:

* **Target Devices**: Android based Smartphones & Tablets
* **Platform**: Android Lollipop (API 21 or later)
* An Internet connection
* GPS (Location)
* Microphone(Audio for Google Assistant)



## 1.3 Scope
__IF-ELSE__ has been approved to build, design and implement an application for a single person usage. This application can be used by people from normal user to a professional business-person.
Workflows can be created using two ways,

#### 1. Configure the **Workflow** manually with three critical configurations:
  
 > **_Activity_**
 >> _Activity_ is action/process performed by _IF-ELSE_, It is mandatory to configure ,as it defines __what__ to do.
 
 > **_Trigger_**
 >> _Triggers_ as the name suggests, It waits for a system Event or Real-Time Clock Event, then sets off the _Activity_ at certain point. It configures __when__ to perform the activity._Triggers_ are mandatory to configure.
 
 > **_Criteria_**
 >> _Criterias_ are configured to define set of rules for an _Activity_ to perform the task on _Trigger_. _Criterias_ can be multiple    and they must be fulfilled in order to start _Activity_.

#### 2. Make use of available WorkFlows and Activities Provided by _IF-ELSE_ application.
This workflows can be used by user by enabling it,this workflows can't be configured manually. Some Examples of this type of Worfklows are,
> **_Profiler that automatically schedules Ringer Profile of mobile phone according to suitable time_**, **_Automatically Message or Email Someone_**, **_To Remind you to do an unfinishing job_**, **_Sync Online data with the Document_** , **_Schedule posts to your Social Media Account_** , **_Send Message and Images to Slack_** , **_Work with Asana_**.
 
##### Features: 
- Configure your own Workflow
- Share workflows if multiple users have same tasks to perform (via. Email).
- Enable/Disable Workflow
- Provides Built-in Worflows for use.
- Authentication Login (via. Google Account)
- It also uses Google API for Google Assistant, Google SpreadSheets for online Documentation and many more. 
- It provides a Feedback, Workflow Request System that interacts with __IF-ELSE__ users on which they can write Feedback and their requests for new Workflows to the Developers.  
 
