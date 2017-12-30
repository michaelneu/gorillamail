# gorillamail

[![Build Status](https://travis-ci.org/michaelneu/gorillamail.svg?branch=master)](https://travis-ci.org/michaelneu/gorillamail)
[![GitHub license](https://img.shields.io/github/license/michaelneu/gorillamail.svg)](https://github.com/michaelneu/gorillamail/LICENSE)
[![GitHub tag](https://img.shields.io/github/tag/michaelneu/gorillamail.svg)](https://github.com/michaelneu/gorillamail)

gorillamail is the final project for the software development course at OTH Regensburg by Prof. Dr. Daniel Jobst, which focused on Java Enterprise Edition and its compounds.

Users of gorillamail can create configurable HTML email templates, which they can send from a dynamic SOAP webservice. As users can create organizations, templates can be shared accross multiple users.

When sending an email, its links are being masked, so clicking on a link can be tracked to investigate whether and how many people actually clicked a link.

Integrating other students' webservices into the project was one of the core objectives of the assignment, thus gorillamail uses both an external payment service to "charge" users for sending mails and an external advertising service to include ads in non-paid emails.


## Setup

gorillamail's email credentials need to be configured in [MailService.java](src/main/java/com/ecorp/gorillamail/services/MailService.java) and the link-redirection in [MailCompiler.java](src/main/java/com/ecorp/gorillamail/services/mail/MailCompiler.java) also needs some tweaks for a manual setup.

After these settings are configured, you can build a `.war` and deploy this on your Java EE server:

```bash
$ mvn compile war:war
```


## License

gorillamail is released under the [MIT license](LICENSE).
