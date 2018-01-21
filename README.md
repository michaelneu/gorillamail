# gorillamail

[![Build Status](https://travis-ci.org/michaelneu/gorillamail.svg?branch=master)](https://travis-ci.org/michaelneu/gorillamail)
[![GitHub license](https://img.shields.io/github/license/michaelneu/gorillamail.svg)](https://github.com/michaelneu/gorillamail/LICENSE)
[![GitHub tag](https://img.shields.io/github/tag/michaelneu/gorillamail.svg)](https://github.com/michaelneu/gorillamail)

gorillamail is the final project for the software development course at OTH Regensburg by Prof. Dr. Daniel Jobst, which focused on Java Enterprise Edition and its compounds.

Users of gorillamail can create configurable HTML email templates, which they can send from a dynamic SOAP webservice. As users can create organizations, templates can be shared accross multiple users.

When sending an email, its links are being masked, so clicking on a link can be tracked to investigate whether and how many people actually clicked a link.

Integrating other students' webservices into the project was one of the core objectives of the assignment, thus gorillamail uses both an external payment service to "charge" users for sending mails and an external advertising service to include ads in non-paid emails.


## Technical details

* Most entities use `FetchType.EAGER` to fetch their collections. This is not the best solution and can cause a single entity to load the entire database into memory, but it prevents ugly workarounds, like calling `collection.size()` to load a collection, and is thus acceptable for this very limited project. In real-world-applications one may use different technologies or libraries to further abstract the JPA and its behaviour
* Bootstrap is used for UI and layout matters, as well as custom CSS using the LESSCSS preprocessor. Furthermore, CodeMirror is used to edit mail templates
* External services used by gorillamail (bannerad and ecorp-bank) were wrapped for a more convenient usage and aren't used directly. To select whether the real external service or a basic mock should be used, modify the [beans.xml](src/main/webapp/WEB-INF/beans.xml)
* Adding and removing users in an organization's settings form uses converters
* The [diagrams provided in the docs folder](docs) required some changes to better meet the requirements of gorillamail:
  * Class diagram
    * Templates don't include headers anymore, and the `sentMails` property was renamed to `mails`
    * The Mail entity now only contains a boolean `ad` property to indicate ads; all other properties displayed in the diagram were dropped
    * Variable, Header and BillingInformation are Embeddables now and don't have an `id` property
  * Component diagram
    * MailServiceIF now throws a MailException and its `sendMails` method was dropped
    * Only the MailService uses external and internal services now
    * Repositories were used to access the EntityManager persistence layer


## Setup

gorillamail's email credentials need to be configured in [MailService.java](src/main/java/com/ecorp/gorillamail/services/MailService.java) and the link-redirection in [MailCompiler.java](src/main/java/com/ecorp/gorillamail/services/mail/MailCompiler.java) also needs some tweaks for a manual setup.

After these settings are configured, you can build a `.war` and deploy this on your Java EE server:

```bash
$ mvn compile war:war
```


## License

gorillamail is released under the [MIT license](LICENSE).
