# LukohSplash

LukohSplahs is based on Android latest architectural components,Jetpack, and follows MVVM design pattern. Also LukohSplahs App Architecture consist of Presentation layer, Domain layer and Repository layer. And new latest technologies were applied into LukohSplahs App as Advanced Android App Architecture. The many advanced functions already were applied into LukohSplahs App. These stuff make Android Apps to be extended being more competitive power and help them to maintain consistency. And I'm also applying Kotlin Language into all modules in Grabph and almost codes are written with Kotlin. I'd like to help someone who are trying to learn Kotlin Language to apply Kotlin Language into their project.

I was confident that I could develop high performance apps using Android architecture components and Kotlin Coroutine and Flow without using LiveData and RxJava, RxKotlin about a couple of months ago. And I proved it.

Now let’s dive into my open-source project, LukohSplash, which is based on the Android MVVM with Clean Architecture and the latest libraries like Jetpack.

Better Android Apps Using MVVM with Clean Architecture

​​If you don’t choose the well-structured architecture for your Android project, you will have a hard time maintaining it as your codebase grows and your team expands.

In this open-source, I've combined MVVM (Model-View-ViewModel or sometimes stylized “the ViewModel pattern”) with Clean Architecture and latest Jetpack libraries. You can also see how this architecture can be used to write decoupled, testable, and maintainable code.
Why MVVM with Clean Architecture?

MVVM decouple all views (i.e. Activitys and Fragments) from all business logics. MVVM is enough for small projects, but when your codebase becomes huge, your ViewModels start bloating. Separating responsibilities becomes hard.

MVVM with Clean Architecture is pretty good in such cases. It goes one step further in separating the responsibilities of your code base. It clearly abstracts the logic of the actions that can be performed in your app. 	 	 							
Basic principles of architecture:	
			
1. ​Separation of concerns (SoC):​ It is a design principle for separating a computer program into distinct sections such that each section addresses a separate concern. A concern is anything that matters in providing a solution to a problem.
						
❏ This principle is closely related to the Single Responsibility Principle of object-oriented programming which states that “every module, class, or function should have responsibility over a single part of the functionality provided by the software, and that responsibility should be entirely encapsulated by the class, module or function.”
						
2. ​Drive UI from a model:​ App should drive the UI from a model, preferably a persistent model. Models are independent from the View objects and app components, so they're unaffected by the app's life-cycle and the associated concerns. Business logic is completely separated from UI. It makes our code very easy to maintain and test.It makes all code very easy to maintain and test.
Advantages of Using Clean Architecture
Your code is even more easily testable than with plain MVVM.
Your code is further decoupled (the biggest advantage.)
The package structure is even easier to navigate.
The project is even easier to maintain.
Your team can add new features even more quickly.
Disadvantages of Clean Architecture
It has a slightly steep learning curve. How all the layers work together may take some time to understand, especially if you are coming from patterns like simple MVVM or MVP.
It adds a lot of extra classes, so it’s not ideal for low-complexity projects.
	 	 							
**Architecture of LukoSplash consists of 3 layer, **Presentation Layer** & **Domain(Business Logic) Layer** & **Data Laery**.

**1. The presentation layer**

The presentation layer is the user layer, the graphical interface that captures the user’s events and shows the results. It also performs operations such as verifying that there are no formatting errors in the user’s data entry and formatting data to be displayed in a certain way.
In this demo App, these operations are shared between the UI layer and the ViewModel layer:
 * The UI layer contains the activities and fragments, capturing user events and displaying data.
 * The ViewModel layer formats the data so that the UI shows them in a certain way and verifies that the user’s entries have the correct format.

**2. The business logic layer**

In this layer all the rules that a business must comply with are business. For this, they receive the data provided by the user and perform the necessary operations. In our example, the ordering of beers from lowest to highest alcoholic strength are the business rules for what the UseCase class will do.
It is the most stable layer and the one that indicates what is happening in the software architecture developed.

**3. The data layer**

In this layer is where the data is and where they can be accessed.
These operations are divided between the Repository layer and Datasource:
 * The Repository layer is the one that performs the logic of data access. Your responsibility is to obtain them and check where they are, deciding where to look at each moment. For example, you can first check the database and, if they are not, search them on the web, save them in the local database and now return the saved data. That is, it defines the flow of access to the data. In our example, it asks beers directly to the data layer that communicates with the API.
 * The Datasource layer is what the implementation performs in order to access the data. In this demo App, it is the one that implements the logic to be able to access the API data of beers. 

![alt Layer Communication](https://raw.githubusercontent.com/Lukoh/LukohSplash/main/Layer%20Communication.jpeg)
			 	 	 								
**Communication between the layers of a clean architecture on Android**

Each layer should talk only with their immediate friends. In this case, if we look at the software architecture scheme:
 * **The UI can only communicate with the ViewModel**
 * **The ViewModel can only communicate with the UseCase**
 * **The UseCase can only communicate with the Repository**
 * **The Repository can only communicate with the Datasource**
 
In this way we are respecting the work in the chain of the factory, each area communicates with the next immediate and never with others.

**In practice:**
* We have a package structure where the classes are created, in which each one implements its responsibility.
* In the user interface layer, the “ui” package, the Activity or Fragment is created. This class must communicate with the ViewModel layer. For this, the Activity must instantiate the ViewModel object and observe the declared LiveData.
* In the presentation logic layer, the “vm” package, we create the ViewModel. This class creates the LiveData that will be observed by the Activity or Fragment. The ViewModel communicates with the UseCase layer, for this you must instantiate the UseCase object.
* In the business logic layer, the “domain — use case” package, we create the UseCase class. This class is instantiated with the following layer, which is the Repository, but it does not do it directly with the object, but it does so with an interface that is in the UseCase package. This is because the UseCase is the most stable layer, which means that the libraries or classes that matter, must also be, and the Repository is one of the most unstable. In this way, it is the Repository that will have an import from the “domain — use case” package and the UseCase will not know it. The UseCase is at the center of the architecture, and this can be seen in the following scheme:
<img src="https://github.com/goforers/Grabph/blob/master/Clean%20Architecture.jpeg?raw=true" alt="Architecture" width="880" />

In this demp App, the Entity is the data model of the business logic layer.
* In the Repository layer, the “repository” package, we create the each Repository class is inherited from BaseRepository class that implements the interface that is in the “domain-use case” package. The Repository calls the Datasource layer, so you must instantiate this class.
* In the Datasource layer, the “datasource” package, we create the Datasource class that develops the logic to get the API data and return them in a data model to be able to work with them. In our example, the Datasource is instantiated with the library with which the API connection is going to be used to consume the data, so the Datasource must instantiate this library in order to call its methods.


## Advanced latest Architecture

Let’s dive migrating from LiveData to Kolin’s Flow

Live data is part of Android Architecture Components which are basically a collection of libraries that help you design robust, testable, and maintainable apps.
This set of libraries contains classes which you can use in your app.
One of these classes is LiveData.
It is an Observable data class — Meaning it can be observed by other components — most profoundly UI controllers (Activities/Fragments). So, instead of having a reference of the activity/fragment in your viewModel( which you shouldn’t have due to leaks), you now have a reference to the viewModel in the activity/fragment
It is Lifecycle aware— Meaning it sends updates to our UI (Activities/Fragments) only when our view is in the active state. (No memory leaks)

Using LiveData provides the following advantages:

No memory leaks
Ensures your UI matches your data state
No crashes due to stopped activities
Always up to date data
Proper configuration changes
Sharing resources

LiveData was introduced as part of Architecture Components and it has integrated well in many projects since that time. It’s helpful for getting rid of callbacks and allows us to observe Lifecycle and act accordingly, getting rid of potential memory leaks.
						
On the other hand, Flow is designed to work in lower levels of architecture and is a reactive stream in the coroutines library which is able to return multiple values from a suspend function. 

Substituting Android’s LiveData: StateFlow or SharedFlow?
Kotlin Coroutines recently introduced two Flow types, SharedFlow and StateFlow, and Android’s community started wondering about the possibilities and implications of substituting LiveData with one of those new types, or both. The two main reasons for that are:

LiveData is closely bound to UI (no natural way to offload work to worker threads), and
LiveData is closely bound to the Android platform.

From these two facts in the Clean Architecture term, I can reach the following conclusion that LiveData works well for Presentation Layer, but not fit well for Domain Layer. It's also not well suited to the Data Layer (repository implementations and data sources), as it typically requires offloading data access operations to worker threads.

Even though the use case of Flow seems very similar to LiveData, it has more advantages like:
						
# ❏ Asynchronous by itself with structured concurrency

# ❏ Simple to transform data with operators like map, filter ... 

# ❏ Easy to test
						
The main purpose of LiveData is not designed to make data transformation and ​LiveData was never designed as a fully fledged reactive stream builder.
LiveData does not support changing threads though. But Flow can easily change the thread we work on using Flow-function and handle back-pressure by calling Flow-function on the Flow chain that skips values emitted by this Flow if the collector is slower than emitter.
						
LiveData is a lifecycle aware component, it is best to use it in view and ViewModel layer. The biggest problem of using LiveData in the repository level is all data transformation will be done on the main thread unless you start a coroutine and do the work inside.
That means we can use the suspend-functions in the data layer. 

I couldn't just substitute LiveData with pure Flow, though. The main issues by using pure Flow as the LiveData's substitute on all app layers are blow:

Flow is stateless (no .value access).
Flow is declarative (cold): a flow builder merely describes what the flow is, and it is only materialized when collected. However, a new Flow is effectively run (materialized) for each collector, meaning upstream (expensive) database access is redundantly and repeatedly run for each collector.
Flow, by itself, does not know anything about Android lifecycles, and does not provide automatic pausing and resuming of collectors upon Android lifecycle state changes.

These are not to be considered as pure Flow inherent defects: these are just characteristics that make it not fit well as a LiveData substitute, but can be powerful in other contexts.
For (3), I could already use LifecycleCoroutineScope extensions such as launchWhenStarted for launching coroutines to collect my flows — these collectors will automatically be paused and resumed in sync with the component's Lifecycle.

And there are problems below:
The first problem with this approach is the handling of the Lifecycle, which LiveData does automatically for us. 
The second problem is below:
Because the Flow is declarative and is only run (materialized) upon collection, if we have multiple collectors, a new flow will be run for each collector, completely independent from each other. Depending on the operations done, such as database or network operations, this can be very ineffective. It can even result in erroneous states.

So I couldn’t use Flow in the UI View Layer.

I applied Shared Flow to solve the above problems.

SharedFlow is a Flow that allows for sharing itself between multiple collectors, so that only one flow is effectively run (materialized) for all of the simultaneous collectors. If you define a SharedFlow that accesses databases and it is collected by multiple collectors, the database access will only run once, and the resulting data will be shared to all collectors.
StateFlow can also be used to achieve the same behavior: it is a specialized SharedFlow with .value (it’s current state) and specific SharedFlow configurations (constraints).

After reading many tech blogs and learning about Koltin-Flow, I have come to the conclusion that the structure like below will be preferred.
The Flow implementation is similar but it doesn’t have LiveData conversions:

![alt StateFlow-Flow-Flow-Flow end-to-end](https://github.com/Lukoh/LukohSplash/blob/main/New-Architecture2.png?raw=true)
Using StateFlow-Flow-Flow-Flow end-to-end

![alt StateFlow-ShardFlow-ShardFlow-Flow end-to-end](https://github.com/Lukoh/LukohSplash/blob/main/New-Architecture1.png?raw=true)
Using StateFlow-ShardFlow-ShardFlow-Flow end-to-end


It may help to think of a SharedFlow as a flow collector itself, that materializes our cold flow upstream into a hot flow, and shares the collected values between the many collectors downstream. 

State flow is a shared flow.
State flow is a special-purpose, high-performance, and efficient implementation of SharedFlow for the narrow, but widely used case of sharing a state. See the SharedFlow documentation for the basic rules, constraints, and operators that are applicable to all shared flows.
State flow always has an initial value, replays one most recent value to new subscribers, does not buffer any more values, but keeps the last emitted one, and does not support reset

Use SharedFlow when you need a StateFlow that adjusts behavior such as additional buffering, playing more values, or omitting initial values. 
However, note the obvious compromise in choosing SharedFlow: you will lose StateFlow<T>.value .

open class TriggerViewModel<Value>(open val useCase: UseCase<Params, Value>) : ViewModel() {
   private var value: Any? = null

   @ExperimentalCoroutinesApi
   open fun pullTrigger(params: Params, lifecycleOwner: LifecycleOwner, doOnResult: (result: Value) -> Unit) {
       lifecycleOwner.lifecycleScope.launch {
           useCase.run(this, params)
               .flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
               .flatMapLatest { resource ->
                   value = resource
                   flow {
                       emit(doOnResult(resource))
                   }
               }.stateIn(
                   scope = viewModelScope,
                   started = Eagerly,
                   initialValue = value
               )
       }
   }
}

	 	 		
The exposed StateFlow will receive updates whenever the user changes or the user’s data in the repository is changed.
The best way to expose data from a ViewModel and collect it from a view is:
✔️ Expose a StateFlow, using the WhileSubscribed strategy, with a timeout. [example]
✔️ Collect with repeatOnLifecycle or flowWithLifecycle. [example]

Any other combination will keep the upstream Flows active, wasting resources:
❌ Expose using WhileSubscribedand collect inside lifecycleScope.launch/launchWhenX
❌ Expose using Lazily/Eagerly and collect with repeatOnLifecycle


Please read Substituting Android’s LiveData: StateFlow or SharedFlow?  If you want to know which to choose, StateFlow or SharedFlow?

I also recommend to visit the Android official below tech blog to know how migrating from LiveData to Kotlin’s Flow and : 
Migrating from LiveData to Kotlin’s Flow

Dependency Injection
What is Dependency Injection?
First, what is a dependency? Any non-trivial software program is going to contain components that pass information and send message calls back and forth between one another.

For example, when using an Object Oriented Programming language (such as Java/Kotlin on Android), objects will call methods on other objects that they have references to. A dependency is when one of the objects depends on the concrete implementation of another object.

The Dependency Inversion Principle
Dependency injection is often discussed in conjunction with one of the five SOLID principles of Object-Oriented Design: the Dependency Inversion principle. For a great introduction to the SOLID principles, particularly on Android, check out this post from Realm on Dependency Inversion.

The gist of the Dependency Inversion principle is that it is important to depend on abstractions rather than concrete implementations.

Dagger2
A fast dependency injector for Android and Kotlin, Java.
Dagger2 is a great library that allows you to @Inject everything you need where you need it and handle the lifecycle of created objects. Dagger2 is used to avoid detail-complicated boilerplate code of connecting architecture elements one to another.

Any Android apps rely on instantiating objects that often require other dependencies. For instance, a Unsplash API client may be built using a networking library such as Retrofit. To use this library, you might also need to add parsing libraries such as Gson. In addition, classes that implement authentication or caching may require accessing shared preferences or other common storage, requiring instantiating them first and creating an inherent dependency chain.

Dagger 2 analyzes these dependencies for you and generates code to help wire them together. While there are other Java dependency injection frameworks, many of them suffered limitations in relying on XML, required validation dependency issues at run-time, or incurred performance penalties during startup. Dagger 2 relies purely on using Java annotation processors and compile-time checks to analyze and verify dependencies. It is considered to be one of the most efficient dependency injection frameworks built to date.
Advantages
Here is a list of other advantages for using Dagger 2:

Simplifies access to shared instances. Just as the ButterKnife library makes it easier to define references to Views, event handlers, and resources, Dagger 2 provides a simple way to obtain references to shared instances. 
Easy configuration of complex dependencies. There is an implicit order in which your objects are often created. Dagger 2 walks through the dependency graph and generates code that is both easy to understand and trace, while also saving you from writing the large amount of boilerplate code you would normally need to write by hand to obtain references and pass them to other objects as dependencies. It also helps simplify refactoring, since you can focus on what modules to build rather than focusing on the order in which they need to be created.
Easier unit and integration testing Because the dependency graph is created for us, we can easily swap out modules that make network responses and mock out this behavior.
Scoped instances Not only can you easily manage instances that can last the entire application lifecycle, you can also leverage Dagger 2 to define instances with shorter lifetimes (i.e. bound to a user session, activity lifecycle, etc.).

Note: I prefer using Dagger 2 for dependency injection in complex projects. But with its extremely steep learning curve, it’s beyond the scope of this article. So if you’re interested in going deeper, I highly recommend Hari Vignesh Jayapalan’s introduction to Dagger 2 and Dependency Injection with Dagger 2, Getting started with Dagger 2.27 on Android by example


Single-Activity Architecture with the Navigation component 

I've applied the Single-Activity Architecture with the Navigation component to this LukohSplash open-source project

Since the announcement of Jetpack in Google I/O 2018, Single Activity Architecture is also mentioned and it seems like the Google team intended to make this architecture more preferable.
I never faced any problem using the Single-Activity Architecture with the Navigation component.
Instead of having one Activity represent one screen, we view an Activity as a big container with the fragments inside the Activity representing the screen.

I've used this architecture in several production and my open-source apps and so far there are no issues. You might wonder what if we want to pass the data back and forth between Fragments like startActivityForResult? 

In this open-source project, I also used the Shared-ViewModel(https://github.com/Lukoh/SingleSharedSample/blob/master/app/src/main/java/com/goforer/singlesharedsample/presentation/vm/SharedViewModel.kt) for communication between fragments. However, recently, Google has just added a new ability to FragmentManager which allows the FragmentManager to act as a central store for fragment results. We can pass the data back and forth between Fragments easily. You can read more about it here https://developer.android.com/training/basics/fragments/pass-data-between#kotlin

If you are about to start the new app, I think it worth a try using Single-Activity Architecture with the Navigation component. However, in the case where you want to use it with the existing app with many Activities, you can start off by transforming the flow to use this architecture. For example, in the authentication flow, instead of having multiple Activity for Login, Sign up, etc, you can combine that into one Activity with Fragment representing each screen in the flow.

Please visit the link below if you'd like to dive deep into Single activity architecture.
(https://developer.android.com/guide/navigation)

MVVM with Clean Architecture: A Solid Combination

My purpose with this open-source project was to understand MVVM with Clean Architecture and latest Jetpack libraries, so I skipped over a few things that you can try to improve it further:

Use Kotlin Coroutine and Flow to remove callbacks and make it a little neater.
Use states to represent your UI. (For that, check out this amazing talk by Jake Wharton.)
Use Dagger 2 to inject dependencies.

This is one of the best and most scalable architectures for Android apps. I hope you enjoyed this article, and I look forward to hearing how you’ve used this approach in your own apps!

