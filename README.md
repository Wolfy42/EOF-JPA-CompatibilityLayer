# EOF-JPA-CompatibilityLayer

Welcome to the ProofOfConcept of a compatibility-layer between EOF and JPA. This is not a complete framework, but a ProofOfConcept which demonstrates the basic idea.

The basic concept is that this is not a further persistency layer which is beside of EOF, but that EOF will be reduced from a 'Entity Object Framework' to an 'Entity Object API'. Every call to EOF will be rerouted to JPA and therefore the JPA-Layer will be underneath the EOF-Layer.

If this CompatibilityLayer would be complete it would be a DropIn-Replacment of the EOF-Fetching-Framework with JPA. 

## Similarity of EOF and JPA

An extended unsynchronized JPA-EntityManager has exactly the same behaviour as an EOF-EditingContext. Most of the examples of JPA in the web are working completely different to EOF because they are using a synchronized EntityManager.

The key to the possibility of a compatibility layer between EOF and JPA is the Unsynchronized-EntityManager-Feature. Without this it would not be possible or very complicated to simulate the exact same behaviour!

The basic difference is:
* synchronized EntityManager: Every EntityManager is backed by a Database-Transaction and therefore needs a very short lifetime. The problem is that with this feature you have to consider detached entities or how to properly reattach entities every time your UseCase is longer (e.g. a Wizard or the Session.DefaultEditingContext)
    * The DB-Transaction starts on the creation of the EntityManager
    * Every fault is in the same DB-Transaction than the complete EntityManager
    * Every save or rollback is backed by this DB-Transaction
    * If the DB-Transaction is rolled back (e.g. failure during saving) all your Entities are doomed
* unsynchronized Entitymanger: This is exactly the same as the EOF-EditingContext:
    * Every fault will be executed in a short DB-Transaction which ends afterwards
    * While working with the Entities all the changes are tracked in memory (no database involved)
    * On saving a new DB-Transaction will be opened and all pending changes will be sent to the database and the transaction will be commited
    * If there is a future change in the entities then this will also be tracked and persisted in the next DB-Transaction

To demonstrate the similarities between EOF and JPA this ProofOfConcept was created!
A great article about the 'unsynchronized' Feature is here: http://www.thoughts-on-java.org/unsychronized-persistencecontext-implement-conversations-jpa/

## Things already working

There are a lot of things already working. There could be not detected edge cases but the basic features are working:
* Creating the JPA-Layer with the EOF-Model and a EOF-Modelgen-File
* Reroute many EOF-Calls to the JPA-Classes
* All CRUD-Operations
* Executing modeled EOF-Fetches with JPA (also including SortOrderings and raw fetches)
* Running in a WO-Application (yes, that's already working)

## Things still open

Due to the fact that this is a ProofOfConcept it was not implemented completely and therefore some features are missing:
* Execute the EOF-Fetches which were create in Java-Code (EOFetchSpecifications). But here the same principle than executing modeled fetches could be used
* Support for DisplayGroups (The problem lies in the fact that the DisplayGroups and EODatabaseDataSource are very hardwired to EOF and every place where the calls should be rerouted to JPA have to be found)
* Support for Enum-Datatypes (But basically here is just the @Convert Annotation missing for the attributs. But the question is if the Converters could be generated with the ModelGen-File)
* Calling the lifecycle-methods of the EOF-Entites with the lifecycle-listeners of the JPA-entites (Both support the same type of listeners). But the current problem is that the JPA-Entity do not know the EOF-Entity and therefore cannot call methods on it. But the JPAEditingContext would know the mapping and therefore it could handle the transfer of the message (e.g. JPA-PrePersist should call the EOF-WillUpdate)
* Calling of the 'validateForSave'-Method before saving of an EO-EnterpriseObject

## Licencing

This project is licenced under the terms of the MIT license.
But keep in mind that Apple has the Copyright of WebObjects and therefore the copyright to the API of the classes in the com.webobjects-package. Apple could claim that the classes in the com.webobjects-package of this project are not allowed to exist!

## Setup to test this in your own application
### Use the manual approach
1. Some internal EOF-Classes have to be replaced with new implementations. The new implementations are in the com.webobjects-packages. If you place them directly in your application-source-folder then they will be used instead of the classes by Webobjects
2. Some Wonder-Classes have to be replaced. They are in the er.extensions.eof-package
3. The new JPA-Classes have to be added. They are in the er.extensions.jpa-package
4. You need a persistence.xml for JPA. This have to include all your entities. An example is in Sources/META-INF/persistence.xml
5. You need to add this persistency.xml to the build. See woproject/classes.include.patternset
6. Add some JPA-Persistency-Provider-JARs to your classpath. For e.g. I was using Hibernate and downloaded the JARs from http://hibernate.org/orm/. 
7. Start the 'EOGenerate' with the EOTemplates in this project (They will create the JPA-Layer)
8. Set the new EditingContext-Factory (ERXEC.setFactory(new JPAFactory());)

### Or the Sample-App
* Have a look at the sample app as a template for setup or just use the sample app for tests

## Some (hopefully interesting) notes
**EO-PK-Table**
* EOF and JPA interpret the data in the table differently. For EOF it is the last used ID. For JPA it is the next free ID. Therefore from switching from EOF to JPA you have to increment all IDs by one.

**Object uniqueness**
* Both EOF and JPA guarantess that the an entity with some specific id is existing exactly one time in the Persistency-Context. If the context is asked again for the some ID then exactly the same object is returned.
* This behaviour is used in the JPAEditingContext with the IdentityHashMap

**Save the changes of the EditingContext**
* To commit the changes to the database the JPAEditingContext just starts a new DB-Transaction and commits it directly. All pending changes in the entities of the JPA-Entities (inserts, updates, deletes) up to this point will be sent to the database

**Revert the changes of the EditingContext**
* All JPA-Entites will be replaced by faults in a new EntityManager and therefore all changes in the entites will be forgotten. So the next usage of the entity will fetch the data from the database.

**EO-Entities wraps JPA-Entities**
* The reason for this is that the EO-Entities live far longer than the JPA-Entities. See notes on rollback!
* For this reason inheritance was not possible (e.g. EO-Entites cannot inherit from JPA-Entities because the JPA-Entities will be replaced but the EO-Entities will live on)

**Execute the EOF-Fetches with JPA**
* Both EOF and JPA have the feature that a fetch can be written as string (e.g. 'select x from y ...'). This string-syntaxes are so similar that an EOF-Query-String can easily be rewritten as JPA-Query-String. This is used for the execution of the modeled fetches.

**Connection releaseMode**
* With the setting of the ReleaseMode to AfterTransaction Hibernate will release the Connection after commiting a transaction. Therefore there can be far more EdidingContexts than JDBC-Connections
