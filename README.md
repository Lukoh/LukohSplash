<p align="left">
  <a href="#"><img alt="Android OS" src="https://img.shields.io/badge/OS-Android-3DDC84?style=flat-square&logo=android"></a>
  <a href="#"><img alt="Languages-Kotlin" src="https://flat.badgen.net/badge/Language/Kotlin?icon=https://raw.githubusercontent.com/binaryshrey/Awesome-Android-Open-Source-Projects/master/assets/Kotlin_Logo_icon_white.svg&color=f18e33"/></a>
  <a href="#"><img alt="PRs" src="https://img.shields.io/badge/PRs-Welcome-3DDC84?style=flat-square"></a>
</p>

<p align="left">
:eyeglasses: The LukohSplash by open-source contributor, Lukoh.
</p><br>

![header](https://capsule-render.vercel.app/api?type=slice&color=gradient&text=%LukohSplash%20%20&height=200&fontSize=100)
# LukohSplash

LukohSplash is based on Android latest architectural components,Jetpack, and follows MVVM design pattern. Also LukohSplash App Architecture consist of Presentation layer, Domain layer and Repository layer. And new latest technologies were applied into LukohSplash App as Advanced Android App Architecture. The many advanced functions already were applied into LukohSplahs App. These stuff make Android Apps to be extended being more competitive power and help them to maintain consistency. And I'm also applying Kotlin Language into all modules in LukohSplash and almost codes are written with Kotlin. I'd like to help someone who are trying to learn Kotlin Language to apply Kotlin Language into their project.

I was confident that I could develop high performance apps using Android architecture components and Kotlin Coroutine and Flow without using LiveData and RxJava, RxKotlin about a couple of months ago. And I proved it.

Now let’s dive into my open-source project, LukohSplash, which is based on the Android MVVM with Clean Architecture and the latest libraries like Jetpack.
And I'm learning Jetpack Compose and will apply it to LukohSplash. 

Here is the [demo video](https://youtu.be/82tpBjbyNQc). 

Please get in touch with me via email if you're interested in my technical experience and all techs which are applied into LukohSplash. Also visit my [LinkedIn profile](https://www.linkedin.com/in/lukoh-nam-68207941/?senderId=lukoh-nam-68207941) if you want to know more about me. Here is my email address below:

lukoh.nam@gmail.com

# Better Android Apps Using MVVM with Clean Architecture

​​If you don’t choose the well-structured architecture for your Android project, you will have a hard time maintaining it as your codebase grows and your team expands.

In this open-source, I've combined MVVM (Model-View-ViewModel or sometimes stylized “the ViewModel pattern”) with Clean Architecture and latest Jetpack libraries. You can also see how this architecture can be used to write decoupled, testable, and maintainable code.
Why MVVM with Clean Architecture?

MVVM decouple all views (i.e. Activitys and Fragments) from all business logics. MVVM is enough for small projects, but when your codebase becomes huge, your ViewModels start bloating. Separating responsibilities becomes hard.

MVVM with Clean Architecture is pretty good in such cases. It goes one step further in separating the responsibilities of your code base. It clearly abstracts the logic of the actions that can be performed in your app. 	 	 							
Basic principles of architecture:	
			
**1. Separation of concerns (SoC):** 
   
    It is a design principle for separating a computer program into distinct sections such that each section addresses a separate concern. 
    A concern is anything that matters in providing a solution to a problem.
   
    ❏ This principle is closely related to the Single Responsibility Principle of object-oriented programming which states that “every module, class, 
    or function should have responsibility over a single part of the functionality provided by the software, and that responsibility should be entirely
    encapsulated by the class, module or function.”
						
**2. Drive UI from a model:** 
    
    App should drive the UI from a model, preferably a persistent model. Models are independent from the View objects and app components, 
    so they're unaffected by the app's life-cycle and the associated concerns. Business logic is completely separated from UI. It makes our code very easy to 
    maintain and test.It makes all code very easy to maintain and test.

**Advantages of Using Clean Architecture**
 * Your code is even more easily testable than with plain MVVM.
 * Your code is further decoupled (the biggest advantage.)
 * The package structure is even easier to navigate.
 * The project is even easier to maintain.
 * Your team can add new features even more quickly.
 
**Disadvantages of Clean Architecture**
 * It has a slightly steep learning curve. How all the layers work together may take some time to understand, especially if you are coming from patterns like simple MVVM or MVP.
 * It adds a lot of extra classes, so it’s not ideal for low-complexity projects.

	 	 							
Architecture of LukoSplash consists of 3 layer, **Presentation Layer** & **Domain(Business Logic) Layer** & **Data Laery**.

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

Each layer should talk only with their immediate friends. In this case, if you look at the software architecture scheme:
 * **The UI can only communicate with the ViewModel**
 * **The ViewModel can only communicate with the UseCase**
 * **The UseCase can only communicate with the Repository**
 * **The Repository can only communicate with the Datasource**
 
In this way I'm respecting the work in the chain of the factory, each area communicates with the next immediate and never with others.

**In practice:**
* I have a package structure where the classes are created, in which each one implements its responsibility.
* In the user interface layer, the “ui” package, the Activity or Fragment is created. This class must communicate with the ViewModel layer. For this, the Activity must instantiate the ViewModel object and observe the declared LiveData.
* In the presentation logic layer, the “vm” package, I created the ViewModel. This class creates the LiveData that will be observed by the Activity or Fragment. The ViewModel communicates with the UseCase layer, for this you must instantiate the UseCase object.
* In the business logic layer, the “domain — use case” package, I created the UseCase class. This class is instantiated with the following layer, which is the Repository, but it does not do it directly with the object, but it does so with an interface that is in the UseCase package. This is because the UseCase is the most stable layer, which means that the libraries or classes that matter, must also be, and the Repository is one of the most unstable. In this way, it is the Repository that will have an import from the “domain — use case” package and the UseCase will not know it. The UseCase is at the center of the architecture, and this can be seen in the following scheme:
<img src="https://github.com/goforers/Grabph/blob/master/Clean%20Architecture.jpeg?raw=true" alt="Architecture" width="880" />

In this demp App, the Entity is the data model of the business logic layer.
* In the Repository layer, the “repository” package, I created the each Repository class is inherited from BaseRepository class that implements the interface that is in the “domain-use case” package. The Repository calls the Datasource layer, so you must instantiate this class.
* In the Datasource layer, the “datasource” package, I created the Datasource class that develops the logic to get the API data and return them in a data model to be able to work with them. In our example, the Datasource is instantiated with the library with which the API connection is going to be used to consume the data, so the Datasource must instantiate this library in order to call its methods.


# Advanced latest Architecture

##  Flow

Let’s dive migrating from LiveData to Kolin’s Flow.

Live data is part of Android Architecture Components which are basically a collection of libraries that help you design robust, testable, and maintainable apps.
This set of libraries contains classes which you can use in your app.
One of these classes is LiveData.
It is an Observable data class — Meaning it can be observed by other components — most profoundly UI controllers (Activities/Fragments). 
So, instead of having a reference of the activity/fragment in your viewModel( which you shouldn’t have due to leaks), you now have a reference to the viewModel in the activity/fragment)
It is Lifecycle aware— Meaning it sends updates to our UI (Activities/Fragments) only when our view is in the active state. (No memory leaks)

Using LiveData provides the following advantages:

 * No memory leaks
 * Ensures your UI matches your data state
 * No crashes due to stopped activities
 * Always up to date data
 * Proper configuration changes
 * Sharing resources

LiveData was introduced as part of Architecture Components and it has integrated well in many projects since that time. It’s helpful for getting rid of callbacks and allows us to observe Lifecycle and act accordingly, getting rid of potential memory leaks.
						
On the other hand, Flow is designed to work in lower levels of architecture and is a reactive stream in the coroutines library which is able to return multiple values from a suspend function. 

Substituting Android’s LiveData: StateFlow or SharedFlow?
Kotlin Coroutines recently introduced two Flow types, SharedFlow and StateFlow, and Android’s community started wondering about the possibilities and implications of substituting LiveData with one of those new types, or both. The two main reasons for that are:

LiveData is closely bound to UI (no natural way to offload work to worker threads), and
LiveData is closely bound to the Android platform.

From these two facts in the Clean Architecture term, I can reach the following conclusion that LiveData works well for Presentation Layer, but not fit well for Domain Layer. It's also not well suited to the Data Layer (repository implementations and data sources), as it typically requires offloading data access operations to worker threads.

Even though the use case of Flow seems very similar to LiveData, it has more advantages like:
						
  - **❏ Asynchronous by itself with structured concurrency**

  - **❏ Simple to transform data with operators like map, filter ...**

  - **❏ Easy to test**
						
The main purpose of LiveData is not designed to make data transformation and ​LiveData was never designed as a fully fledged reactive stream builder.
LiveData does not support changing threads though. But Flow can easily change the thread I work on using Flow-function and handle back-pressure by calling Flow-function on the Flow chain that skips values emitted by this Flow if the collector is slower than emitter.
						
LiveData is a lifecycle aware component, it is best to use it in view and ViewModel layer. The biggest problem of using LiveData in the repository level is all data transformation will be done on the main thread unless you start a coroutine and do the work inside.
That means I can use the suspend-functions in the data layer. 

I couldn't just substitute LiveData with pure Flow, though. The main issues by using pure Flow as the LiveData's substitute on all app layers are blow:

Flow is stateless (no .value access).
Flow is declarative (cold): a flow builder merely describes what the flow is, and it is only materialized when collected. However, a new Flow is effectively run (materialized) for each collector, meaning upstream (expensive) database access is redundantly and repeatedly run for each collector.
Flow, by itself, does not know anything about Android lifecycles, and does not provide automatic pausing and resuming of collectors upon Android lifecycle state changes.

These are not to be considered as pure Flow inherent defects: these are just characteristics that make it not fit well as a LiveData substitute, but can be powerful in other contexts.
For (3), I could already use LifecycleCoroutineScope extensions such as launchWhenStarted for launching coroutines to collect my flows — these collectors will automatically be paused and resumed in sync with the component's Lifecycle.

And there are problems below:
The first problem with this approach is the handling of the Lifecycle, which LiveData does automatically for us. 
The second problem is below:
Because the Flow is declarative and is only run (materialized) upon collection, if I have multiple collectors, a new flow will be run for each collector, completely independent from each other. Depending on the operations done, such as database or network operations, this can be very ineffective. It can even result in erroneous states.

So I couldn’t use Flow in the UI View Layer.

I applied Shared Flow to solve the above problems.

SharedFlow is a Flow that allows for sharing itself between multiple collectors, so that only one flow is effectively run (materialized) for all of the simultaneous collectors. If you define a SharedFlow that accesses databases and it is collected by multiple collectors, the database access will only run once, and the resulting data will be shared to all collectors.
StateFlow can also be used to achieve the same behavior: it is a specialized SharedFlow with .value (it’s current state) and specific SharedFlow configurations (constraints).

After reading many tech blogs and learning about Koltin-Flow, I have come to the conclusion that the structure like below will be preferred.
The Flow implementation is similar but it doesn’t have LiveData conversions:

The exposed StateFlow will receive updates whenever the user changes or the user’s data in the repository is changed.

![alt StateFlow-Flow-Flow-Flow end-to-end](https://github.com/Lukoh/LukohSplash/blob/main/New-Architecture2.png?raw=true)
**Using StateFlow-ShardFlow-ShardFlow-Flow end-to-end**

Let you hit [Main Branch](https://github.com/Lukoh/LukohSplash/tree/main) or [Challenge branch](https://github.com/Lukoh/LukohSplash/tree/Challenge) if you're interested in above architecture.

![alt StateFlow-ShardFlow-ShardFlow-Flow end-to-end](https://github.com/Lukoh/LukohSplash/blob/main/New-Architecture1.png?raw=true)
**Using StateFlow-Flow-Flow-Flow end-to-end**\

Let you hit [StateFlow branch](https://github.com/Lukoh/LukohSplash/tree/origin/StateFlow) if you're interested in above architecture.

It may help to think of a SharedFlow as a flow collector itself, that materializes our cold flow upstream into a hot flow, and shares the collected values between the many collectors downstream. 

State flow is a shared flow.
State flow is a special-purpose, high-performance, and efficient implementation of SharedFlow for the narrow, but widely used case of sharing a state. See the SharedFlow documentation for the basic rules, constraints, and operators that are applicable to all shared flows.
State flow always has an initial value, replays one most recent value to new subscribers, does not buffer any more values, but keeps the last emitted one, and does not support reset

Use SharedFlow when you need a StateFlow that adjusts behavior such as additional buffering, playing more values, or omitting initial values. 
However, note the obvious compromise in choosing SharedFlow: you will lose StateFlow<T>.value .

```kotlin	
@OptIn(ExperimentalCoroutinesApi::class)
open class MediatorViewModel(useCase: UseCase<Resource>, params: Params) : ViewModel() {
    val value = useCase.run(viewModelScope, params).flatMapLatest {
        flow {
            emit(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = Resource().loading(LOADING)
    )
}	
```
	 	 		
The exposed StateFlow will receive updates whenever the user changes or the user’s data in the repository is changed.
The best way to expose data from a ViewModel and collect it from a view is:
	
 - ✔️ Expose a StateFlow, using the WhileSubscribed strategy, with a timeout. [example]
	
 - ✔️ Collect with repeatOnLifecycle or flowWithLifecycle. [example]

Any other combination will keep the upstream Flows active, wasting resources:
	
 - ❌ Expose using WhileSubscribedand collect inside lifecycleScope.launch/launchWhenX
	
 - ❌ Expose using Lazily/Eagerly and collect with repeatOnLifecycle

	
<span style="#b68834">**CAUTION!**</span> 	

Please avoid creating new instances on each function call**
	
NEVER use shareIn or stateIn to create a new flow that’s returned when calling a function. That’d create a new SharedFlow or StateFlow on each function invocation that will remain in memory until the scope is cancelled or is garbage collected when there are no references to it.	

```kotlin
open class MediatorViewModel(useCase: UseCase<Resource>, params: Params) : ViewModel() {
    // DO NOT USE shareIn or stateIn in a function like this.
    // It creates a new SharedFlow/StateFlow per invocation which is not reused!
    private var value: Value? = null		
	
    open fun pullTrigger(params: Params, lifecycleOwner: LifecycleOwner, doOnResult: (result: Value) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            useCase.run(this, params)
                .flatMapLatest { resource ->
                    value = resource
                    flow {
                        emit(doOnResult(resource))
                    }
                }.shareIn(
                    scope = lifecycleOwner.lifecycleScope,
                    started = Eagerly,
                    replay = 1
                )
        }
    }    

    // DO USE shareIn or stateIn in a property
    val value = useCase.run(viewModelScope, params).flatMapLatest {
        flow {
            emit(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = Resource().loading(LOADING)
    )
}
```

Please read [Things to know about Flow’s shareIn and stateIn operators](https://medium.com/androiddevelopers/things-to-know-about-flows-sharein-and-statein-operators-20e6ccb2bc74) if you learn more as I mentioned above. I'm confident it helps you to use StateFlow or SharedFlow very well.
	
If you want to know which to choose, StateFlow or SharedFlow, please read [Substituting Android’s LiveData: StateFlow or SharedFlow?](https://proandroiddev.com/should-we-choose-kotlins-stateflow-or-sharedflow-to-substitute-for-android-s-livedata-2d69f2bd6fa5)

I also recommend to visit the Android official below tech blog to know how migrating from LiveData to Kotlin’s Flow and : 
[Migrating from LiveData to Kotlin’s Flow](https://medium.com/androiddevelopers/migrating-from-livedata-to-kotlins-flow-379292f419fb)

## Dependency Injection

What is Dependency Injection?
First, what is a dependency? Any non-trivial software program is going to contain components that pass information and send message calls back and forth between one another.

For example, when using an Object Oriented Programming language (such as Java/Kotlin on Android), objects will call methods on other objects that they have references to. A dependency is when one of the objects depends on the concrete implementation of another object.

The Dependency Inversion Principle
Dependency injection is often discussed in conjunction with one of the five SOLID principles of Object-Oriented Design: the Dependency Inversion principle. For a great introduction to the SOLID principles, particularly on Android, check out this post from Realm on Dependency Inversion.

The gist of the Dependency Inversion principle is that it is important to depend on abstractions rather than concrete implementations.

### - Dagger2
A fast dependency injector for Android and Kotlin, Java.
Dagger2 is a great library that allows you to @Inject everything you need where you need it and handle the lifecycle of created objects. Dagger2 is used to avoid detail-complicated boilerplate code of connecting architecture elements one to another.

Any Android apps rely on instantiating objects that often require other dependencies. For instance, a Unsplash API client may be built using a networking library such as Retrofit. To use this library, you might also need to add parsing libraries such as Gson. In addition, classes that implement authentication or caching may require accessing shared preferences or other common storage, requiring instantiating them first and creating an inherent dependency chain.

Dagger2 analyzes these dependencies for you and generates code to help wire them together. While there are other Java dependency injection frameworks, many of them suffered limitations in relying on XML, required validation dependency issues at run-time, or incurred performance penalties during startup. Dagger 2 relies purely on using Java annotation processors and compile-time checks to analyze and verify dependencies. It is considered to be one of the most efficient dependency injection frameworks built to date.
Advantages
Here is a list of other advantages for using Dagger2:

Simplifies access to shared instances. Just as the ButterKnife library makes it easier to define references to Views, event handlers, and resources, Dagger2 provides a simple way to obtain references to shared instances. 
Easy configuration of complex dependencies. There is an implicit order in which your objects are often created. Dagger2 walks through the dependency graph and generates code that is both easy to understand and trace, while also saving you from writing the large amount of boilerplate code you would normally need to write by hand to obtain references and pass them to other objects as dependencies. It also helps simplify refactoring, since you can focus on what modules to build rather than focusing on the order in which they need to be created.
Easier unit and integration testing Because the dependency graph is created for us, You can easily swap out modules that make network responses and mock out this behavior.
Scoped instances Not only can you easily manage instances that can last the entire application lifecycle, you can also leverage Dagger2 to define instances with shorter lifetimes (i.e. bound to a user session, activity lifecycle, etc.).

Note: I prefer using Dagger2 for dependency injection in complex projects. But with its extremely steep learning curve, it’s beyond the scope of this article. So if you’re interested in going deeper, I highly recommend [Hari Vignesh Jayapalan’s introduction to Dagger2](https://medium.com/@harivigneshjayapalan/dagger-2-for-android-beginners-introduction-be6580cb3edb) and [Dependency Injection with Dagger2](https://developer.android.com/training/dependency-injection/dagger-basics), getting started with Dagger2 on Android by example
	
Since version 2.31,  Dagger2 gives us the ability to use assisted injection. And now you can create yoour view models in more simple way like below.
Here is my ViewModel: it uses params which deliver parameters to REST APIs and an useCase. This means you can pass the parameters to REST APIs as the query or paths. That is, it's possible to achieve passing everything manually.
	
To use Dagger’s assisted injection, annotate the constructor of an object with @AssistedInject and annotate any assisted parameters with @Assisted, as shown below:

```kotlin
class GetPhotosViewModel
@AssistedInject
constructor(
    useCase: GetPhotosUseCase,
    @Assisted private val params: Params
) : MediatorViewModel(useCase, params) {
    ...
}
```

Next, define a factory that can be used to create an instance of the object. The factory must be annotated with @AssistedFactory and must contain an abstract method that returns the @AssistedInject type and takes in all @Assisted parameters defined in its constructor (in the same order). This is shown below:

```kotlin
@AssistedFactory
interface AssistedPhotosFactory {
    fun create(params: Params): GetPhotosViewModel
}

companion object {
    fun provideFactory(assistedFactory: AssistedPhotosFactory, params: Params) = object : Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return assistedFactory.create(params) as T
        }
    }	
}
```

Finally, Dagger will create the implementation for the assisted factory and provide a binding for it. The factory can be injected as a dependency as shown below.

```kotlin
class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {
    @Inject
    lateinit var getPhotosViewModelFactory: GetPhotosViewModel.AssistedPhotosFactory
	
    ...

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getPhotos(page: Int) {
        val getPhotosViewModel: GetPhotosViewModel by viewModels {
            GetPhotosViewModel.provideFactory(
                getPhotosViewModelFactory,
                Params(Query().apply {
                    firstParam = page
                    secondParam = NetworkBoundWorker.NONE_ITEM_COUNT
                    thirdParam = NetworkBoundWorker.LATEST
                })
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getPhotosViewModel.value.collect { resource ->
                    when (resource.getStatus()) {
                        Status.SUCCESS -> {
                            resource.getData()?.let {
                                binding.swipeRefreshContainer.isRefreshing = false
                                @Suppress("UNCHECKED_CAST")
                                val photos = resource.getData() as? PagingData<Photo>

                                viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                                    photoAdapter?.submitData(photos!!)
                                }
                            }
                        }

                        Status.ERROR -> {
                            binding.swipeRefreshContainer.isRefreshing = false
                            showErrorPopup(resource.getMessage()!!) {}

                        }

                        Status.LOADING -> {
                            binding.swipeRefreshContainer.isRefreshing = true
                        }
                    }
                }
            }
        }
    }	
}
```	

## Single-Activity Architecture with the Navigation component 

I've applied the Single-Activity Architecture with the Navigation component to this LukohSplash open-source project

Since the announcement of Jetpack in Google I/O 2018, Single Activity Architecture is also mentioned and it seems like the Google team intended to make this architecture more preferable.
I never faced any problem using the Single-Activity Architecture with the Navigation component.
Instead of having one Activity represent one screen, I view an Activity as a big container with the fragments inside the Activity representing the screen.

I've used this architecture in several production and my open-source apps and so far there are no issues. You might wonder what if you want to pass the data back and forth between Fragments like startActivityForResult? 
If you are about to start the new app, I think it worth a try using Single-Activity Architecture with the Navigation component. However, in the case where you want to use it with the existing app with many Activities, you can start off by transforming the flow to use this architecture. For example, in the authentication flow, instead of having multiple Activity for Login, Sign up, etc, you can combine that into one Activity with Fragment representing each screen in the flow.
	
The Navigation Architecture Component simplifies the implementation of navigation in an Android app. It also ensures a consistent and predictable user experience by adhering to an established set of principles.

### - Advantages
The concept behind the Navigation Architecture Component is to have the developer use a single Activity (Fragments only), to achieve various benefits. These include reduced development time, easy animations between Fragments, and increased app performance.

Navigation Architecture Component has been launched to solve a lot of Android app navigation problems. We can see them described below.

* Implementation
	
  Android Navigation Component accelerates app development and is easy to be implemented. It entails not much more than a few concepts and a config file.

* Fragment transactions
	
  You may have tried Fragment transactions before. If so, you know that a lot of your code is needed to achieve the result. If you've already learned the Android   
  Navigation Component, you no longer need to care about adding, replacing and removing Fragments, because the framework does this for us.

* Passing arguments in a safe way
	
  Now there is a way to ensure that the data being passed from one Fragment will be received by another Fragment without cast.

* Handling up/back button and back stack
	
  Sometimes this gives us a headache. Now it’s only necessary to specify the app navigation within the config file.

* Animations
	
  Animations are also specified in a simple way, within the config file, making the code cleaner. Just beautiful.

* Deep links
	
  In Android, a deep link is a link that takes the user directly to a specific destination within an app. The framework lets you easily create deep links with the   use of single line of code within the config file, without having to handle it manually.

* Handling drawer navigation and bottom navigation
	
  The framework already has support for these navigation components, keeping it concise and complete.

* Tests
	
  We all know the importance of testing things before launching an app. All of the things that are offered by the framework are already well tested. This way, the   important test becomes the interactions between the Fragments.

Please visit the link below if you'd like to dive deep into [Single activity architecture](https://developer.android.com/guide/navigation).
	
## ViewModel
	
I made 3 ViewModel to decouple the role of ViewModels.
The role of each ViewModelbelow are below:	
	
### - Shared-ViewModel
	
In this open-source project, I also used the [Shared-ViewModel](https://github.com/Lukoh/SingleSharedSample/blob/master/app/src/main/java/com/goforer/singlesharedsample/presentation/vm/SharedViewModel.kt) for communication between fragments. However, recently, Google has just added a new ability to FragmentManager which allows the FragmentManager to act as a central store for fragment results. You can pass the data back and forth between Fragments easily. You can read more about it [here](https://developer.android.com/training/basics/fragments/pass-data-between)
	
### - Mediator-ViewModel
	
In this open-source project, I also used the [Mediator-ViewModel](https://github.com/Lukoh/LukohSplash/blob/main/app/src/main/java/com/goforer/lukohsplash/presentation/vm/MediatorViewModel.kt) for delivering data-information as parmeters or paths being used in REST APIs to UseCase and Repository. I implemented this ViewModel with a couple of methods.
	
### - Processor-ViewModel
	
In this open-source project, I also used the [Processor-ViewModel](https://github.com/Lukoh/LukohSplash/blob/main/app/src/main/java/com/goforer/lukohsplash/presentation/vm/ProcessorViewModel.kt) for hanlding the business logic. Once dealing with the business logic, the work of this ViewModel which is tied up with each UseCase in the processor package get back to UI with the result and update or refresh the UI. I implemented this ViewModel with a couple of methods. Business logic is completely separated from UI. It makes our code very easy to 
maintain and test.It makes all code very easy to maintain and test. Please see the below code if you'd like to know how Processor-ViewModel and the UseCase in in the processor package get worked.

```kotlin
class DownloadPhotoViewModel
@AssistedInject
constructor(
    useCase: DownloadPhotosUseCase,
    @Assisted private val params: Params,
) : ProcessorViewModel<Int>(useCase, params) {
    @AssistedFactory
    interface AssistedDownloadPhotoFactory {
        fun create(params: Params): DownloadPhotoViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedDownloadPhotoFactory, params: Params) = object : Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(params) as T
            }
        }
    }
}
```	

```kotlin
@Singleton
class DownloadPhotosUseCase
@Inject
constructor(
    private val context: Context,
    private val downloaderQueryInterface: DownloaderQueryWrapper
) : UseCase<Int>() {
    private lateinit var params: Params

    companion object {
        internal const val FILE_EXISTED = 9999
    }

    override fun run(lifecycleScope: CoroutineScope, params: Params) = flow {
        var downloading = true
        val downloadManager = params.query.firstParam as DownloadManager
        val url = params.query.secondParam as String
        val file = params.query.thirdParam as File
        val fileName = url.substring(url.lastIndexOf("/") + 1).take(19)
        val myFile =
            File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "${fileName}.jpg")

        this@DownloadPhotosUseCase.params = params
        if (myFile.exists()) {
            emit(FILE_EXISTED)
        } else {
            val query = downloaderQueryInterface.takeQuery(
                downloadManager,
                SaveFileInfo(url, file, fileName)
            )

            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(
                        cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    )
                    == DownloadManager.STATUS_SUCCESSFUL
                ) {
                    downloading = false
                }

                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                emit(status)
                cursor.close()

            }
        }
    }.shareIn(
        scope = lifecycleScope,
        started = WhileSubscribed(5000),
        replay = 1
    )
}
```
	
## Communicating with fragments
	
I introduce  two way to share or pass data between destinations such as fragments.


###  - Using ViewModel

ViewModel is an ideal choice when you need to share, pass the data or information between multiple fragments or between fragments and their host activity. ViewModel objects store and manage UI data.
The following sections show you how to use ViewModel to communicate between your fragments.

####  * Share data between fragments

A couple of or more fragments in the same activity often need to pass information or data with each other. For example, imagine one fragment that displays a list and another that allows the user to apply various filters to the list. This case might not be trivial to implement without the fragments communicating directly, which would mean they are no longer self-contained. Additionally, both fragments must handle the scenario where the other fragment is not yet created or visible.

These fragments can share a ViewModel using their activity scope to handle this communication. By sharing the ViewModel in this way, the fragments do not need to know about each other, and the activity does not need to do anything to facilitate the communication.

The following code shows how a couple of or more fragments can use a shared ViewModel to communicate:

```kotlin
@Singleton
class SharedPhotoIdViewModel
@Inject
constructor() : SharedViewModel<String>()

The following code to to share the data to PhotoDetailFragment to handle some work with it.

class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {
    ...
    @Inject
    internal lateinit var sharedPhotoIdViewModel: SharedPhotoIdViewModel
    ...
    ...
    photoAdapter = photoAdapter ?: PhotosAdapter(homeActivity) { itemView, item ->
        sharedPhotoIdViewModel.share(item.id)
        itemView.findNavController().navigate(
            PhotosFragmentDirections.actionPhotosFragmentToPhotoDetailFragment()
        )
    }
    ...   
}

The following code to to receive the shared data from  PhotosFragment to handle some work with it.

class PhotoDetailFragment : BaseFragment<FragmentPhotoDetailBinding>() {
    ...
    @Inject
    lateinit var sharedPhotoIdViewModel: SharedPhotoIdViewModel
    ...
    ...
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ...
        observePhotoID()
        ...
    }

    ...
    private fun observePhotoID() {
        sharedPhotoIdViewModel.shared {
            // handle some work with the shared data
        }
    }
    ...
}
```

You can observe the shared data or information in any fragments if you want to receive these stuff. Just declare SharedView model like above code in the fragment and implement the below code in any fragments to get the shared data or information:

```kotlin
sharedPhotoIdViewModel.shared {
    // handle some work with the shared data
}
```

Than's all to share or pass the data or information between fragments with ViewModel. It's easy.

I implemented [SharedViewModel](). Please read SharedModel section in ViewModel section, if you'd like to know what [SharedModel]() is. It's very useful to understand above code.


### - Using Navigation

Navigation allows you to attach data to a navigation operation by defining arguments for a destination. For example, a user profile destination might take a user ID argument to determine which user to display.
In general, you should strongly prefer passing only the minimal amount of data between destinations. For example, you should pass a key to retrieve an object rather than passing the object itself, as the total space for all saved states is limited on Android. 

To pass data between destinations, first define the argument by adding it to the destination that receives it.

Here is a example which is used in LukohSplash shown below:

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/home_nav"
    app:startDestination="@+id/photo_list">
    ...

    <fragment
        android:id="@+id/collection_photo_list"
        android:name="com.goforer.lukohsplash.presentation.ui.user.UserCollectionPhotosFragment"
        tools:layout="@layout/fragment_collection_photos">
        <argument
            android:name="collectionId"
            app:argType="string" />
        <argument
            android:name="collectionTitle"
            app:argType="string" />

    </fragment>

</navigation>
```

I used Safe Args to share or pass data between with type safety.

The Navigation component has a Gradle plugin called Safe Args that generates simple object and builder classes for type-safe navigation and access to any associated arguments. Safe Args is strongly recommended for navigating and passing data, because it ensures type-safety.

In some cases, for example if you are not using Gradle, you can't use the Safe Args plugin. In these cases, you can use Bundles to directly pass data.

To add Safe Args to your project, please visit [Use Safe Args to pass data with type safety](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args)

After enabling Safe Args, your generated code contains the following type-safe classes and methods for each action as well as with each sending and receiving destination.

A class is created for each destination where an action originates. The name of this class is the name of the originating destination, appended with the word "Directions". For example, if the originating destination is a fragment that is named SpecifyAmountFragment, the generated class would be called SpecifyAmountFragmentDirections.

This class has a method for each action defined in the originating destination.

For each action used to pass the argument, an inner class is created whose name is based on the action. For example, if the action is called confirmationAction, the class is named ConfirmationAction. If your action contains arguments without a defaultValue, then you use the associated action class to set the value of the arguments.

A class is created for the receiving destination. The name of this class is the name of the destination, appended with the word "Args". For example, if the destination fragment is named ConfirmationFragment, the generated class is called ConfirmationFragmentArgs. Use this class's fromBundle() method to retrieve the arguments.

The following code shows you how to use these methods to set an argument and pass it to the navigate() method:

[UserCollectionFragment]()

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    ...
    collectionAdapter =
            collectionAdapter ?: UserCollectionAdapter(homeActivity) { itemView, item ->
                itemView.findNavController().navigate(
                    UserFragmentDirections.actionUserFragmentToUserCollectionPhotosFragment(
                        item.id, item.title
                    )
                )
            }
    ...
}
```

In your receiving destination’s code, use the getArguments() method to retrieve the bundle and use its contents. When using the -ktx dependencies, Kotlin users can also use the by navArgs() property delegate to access arguments.

[UserCollectionPhotosFragment]()

```kotlin
class UserCollectionPhotosFragment : BaseFragment<FragmentCollectionPhotosBinding>() {
    ...
    private val args: UserCollectionPhotosFragmentArgs by navArgs()
    ...
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ...
        getCollectionPhotos(args.collectionId, PhotosPagingSource.nextPage)
        ...
    }		
}
```

Use Safe Args with a global action
When using Safe Args with a global action, you must provide an android:id value for your root <navigation> element, as shown in the following code:

[home_nav.xml]()

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/home_nav"
    app:startDestination="@+id/photo_list">

    <fragment
        android:id="@+id/photo_list"
        android:name="com.goforer.lukohsplash.presentation.ui.home.PhotosFragment"
        tools:layout="@layout/fragment_photos">
        <action
            android:id="@+id/action_PhotosFragment_to_PhotoDetailFragment"
            app:destination="@id/photo_detail" />
    </fragment>

    <fragment
        android:id="@+id/photo_detail"
        android:name="com.goforer.lukohsplash.presentation.ui.photo.PhotoDetailFragment"
        tools:layout="@layout/fragment_photo_detail">
        <action
            android:id="@+id/action_PhotoDetailFragment_to_UserFragment"
            app:destination="@id/user_info" />
        <action
            android:id="@+id/action_PhotoDetailFragment_to_PhotoViewerFragment"
            app:destination="@id/photo_viewer" />
    </fragment>

    <fragment
        android:id="@+id/photo_viewer"
        android:name="com.goforer.lukohsplash.presentation.ui.photo.PhotoViewerFragment"
        tools:layout="@layout/fragment_photo_viewer">
        <argument
            android:name="photoUrl"
            app:argType="string" />
    </fragment>

    <fragment
        android:id="@+id/user_info"
        android:name="com.goforer.lukohsplash.presentation.ui.user.UserFragment"
        tools:layout="@layout/fragment_user">

        <action
            android:id="@+id/action_UserFragment_to_PhotoViewerFragment"
            app:destination="@id/photo_viewer" />

        <action
            android:id="@+id/action_UserFragment_to_UserCollectionPhotosFragment"
            app:destination="@id/collection_photo_list" />

    </fragment>

    <fragment
        android:id="@+id/collection_photo_list"
        android:name="com.goforer.lukohsplash.presentation.ui.user.UserCollectionPhotosFragment"
        tools:layout="@layout/fragment_collection_photos">
        <argument
            android:name="collectionId"
            app:argType="string" />
        <argument
            android:name="collectionTitle"
            app:argType="string" />

    </fragment>

</navigation>
```

Navigation generates a Directions class for the <navigation> element that is based on the android:id value. For example, if you have a <navigation> element with android:id=@+id/main_nav, the generated class is called MainNavDirections. 

If you'd like to learn Navigation, I recommend you to read [this document of Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) 


Conclusion

If you need to pass large amounts of data or object, consider using a ViewModel as described above.

Or if you need to pass a couple of data or parameters, consider using Navigation as mentioned above.
	
	
## Paging3
	
The Jetpack Component Library helps you load and display pages of data from a larger dataset from local storage or over network. This approach allows your app to use both network bandwidth and system resources more efficiently. The components of the Paging library are designed to fit into the recommended Android app architecture, integrate cleanly with other Jetpack components, and provide first-class Kotlin support.
	
Also the Paging 3.0 Jetpack Component Library is a major update to the previous version of the Paging library and has been completely re-implemented from the previous version of the Paging library. Full support for Kotlin coroutines and other reactive streams like RxJava and LiveData. It also has built-in error handling and support for managing loading state, making it very easy to implement paging in your app.
	
You can check out it how to implment all code of Paging3. Please refer to code below:

### - Get the PagingData in Repository
	
Now I created an instance of Pager in my Repository to get a stream of data from the PhotosPagingSource that I just created.

[GetPhotosRepository Code](https://github.com/Lukoh/LukohSplash/blob/main/app/src/main/java/com/goforer/lukohsplash/data/repository/remote/home/GetPhotosRepository.kt)
	
```kotlin
@Singleton
class GetPhotosRepository
@Inject
constructor(val pagingSource: PhotosPagingSource) : Repository<Resource>() {
    @ExperimentalCoroutinesApi
    override fun doWork(lifecycleScope: CoroutineScope, query: Query) = object :
        NetworkBoundWorker<PagingData<Photo>, MutableList<Photo>>(false, lifecycleScope) {
        override fun request() = restAPI.getPhotos(YOUR_ACCESS_KEY, 1, NONE_ITEM_COUNT, LATEST)

        override fun load(value: MutableList<Photo>, itemCount: Int) = Pager(
            config = PagingConfig(
                pageSize = itemCount,
                prefetchDistance = itemCount,
                initialLoadSize = itemCount
            )
        ) {
            pagingSource.setData(query, value)
            pagingSource
        }.flow.cachedIn(lifecycleScope)
    }.asSharedFlow
}
```
	
 * The Pager object calls the load() method from the MoviePagingSource object, providing it with the LoadParams object and receiving the LoadResult object in return.
	
 * You also have to provide configurations such as pageSize with the PagingConfig object.

 * The cachedIn(viewModelScope) caches the data from the MoviePagingSource to survive the screen orientation changes.

### - Create a Data Source

Unlike the previous versions of Paging library, in Paging3, I have to implement a PagingSource<Key, Value> to define a data source. The PagingSource takes two parameters a Key and a Value. The Key parameter is the identifier of the data to be loaded such as page number and the Value is the type of the data itself.

	
[PhotosPagingSource Code](https://github.com/Lukoh/LukohSplash/blob/main/app/src/main/java/com/goforer/lukohsplash/data/repository/paging/source/home/PhotosPagingSource.kt)

```kotlin
@Singleton
class PhotosPagingSource
@Inject
constructor() : BasePagingSource<Int, Photo>() {
    override fun setData(query: Query, value: MutableList<Photo>) {
        this.query = query
        pagingList = value
    }

    @SuppressWarnings("unchecked")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            params.key.isNullOnFlow({}, {
                restAPI.getPhotos(
                    YOUR_ACCESS_KEY, params.key?.plus(1), query.secondParam as Int,  query.thirdParam as String
                ).collect { apiResponse ->
                    pagingList = when (apiResponse) {
                        is ApiSuccessResponse -> {
                            apiResponse.body
                        }

                        is ApiEmptyResponse -> {
                            errorMessage = ERROR_MESSAGE_PAGING_EMPTY
                            arrayListOf()
                        }

                        is ApiErrorResponse -> {
                            errorMessage = apiResponse.errorMessage
                            arrayListOf()
                        }
                    }
                }
            })

            if (pagingList.isNotEmpty())
                LoadResult.Page(
                    data = pagingList,
                    prevKey = null,
                    nextKey = params.key?.plus(1) ?: 1
                )
            else
                LoadResult.Error(Throwable(errorMessage))
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            // Handle errors in this block
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
```

### - Display data in RecyclerView
	
First I have to create a RecyclerView'S adapter class which extends from the PagingDataAdapter. This is the same as a normal RecyclerView adapter. The PagingDataAdapter takes two parameters, the first one is the type of the data(which in our case is the Movie object), and the second one is a RecyclerView.ViewHolder.

Please refer to [this PhotosAdapter](https://github.com/Lukoh/LukohSplash/blob/main/app/src/main/java/com/goforer/lukohsplash/presentation/ui/home/adapter/PhotosAdapter.kt) if you'd like to see the code of it.
	
Finally, I implemented the coee in the fragment to show the list of all photos.

[PhotosFragment](https://github.com/Lukoh/LukohSplash/blob/main/app/src/main/java/com/goforer/lukohsplash/presentation/ui/home/PhotosFragment.kt)	

```kotlin
@OptIn(ExperimentalCoroutinesApi::class)
private fun getPhotos() {
    ...
    getPhotosViewModel.value.collect { resource ->
	...
	viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            photoAdapter?.submitData(photos!!)
        }
    }
}
```

Please read [this page](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) and [How to Use the Paging 3 Library in Android](https://proandroiddev.com/how-to-use-the-paging-3-library-in-android-5d128bb5b1d8) if you'd like to learn how to apply and implement it.
	
## ViewPager2
	
ViewPager2 is an improved version of the ViewPager library that offers enhanced functionality and addresses common difficulties with using ViewPager. If your app already uses ViewPager, [read this page](https://developer.android.com/training/animation/vp2-migration) to learn more about migrating to ViewPager2.

If you want to use ViewPager2 in your app and are not currently using ViewPager, read [Slide between fragments using ViewPager2](https://developer.android.com/training/animation/screen-slide-2) and [Create swipe views with tabs using ViewPager2](https://developer.android.com/guide/navigation/navigation-swipe-view-2) for more information.
	
## View Binding
	
View binding is a feature that allows you to more easily write code that interacts with views. Once view binding is enabled in a module, it generates a binding class for each XML layout file present in that module. An instance of a binding class contains direct references to all views that have an ID in the corresponding layout.

In most cases, view binding replaces findViewById.

In comparison to the well-known methods such as Kotlin synthetics, Butterknife and findViewById, it provides a safer and more concise way of handling view interactions inside your views. Before comparing them all side by side, let’s dive in the features of View Binding.
	
According to Google, this new approach has the best of all the worlds. So, you should use it wherever you can.

### - Main advantages
	
The new ViewBinding feature has some advantages compared to the traditional approach and some of the libraries:
 * **Null safety**: view binding creates direct references to views, there’s no risk of a NullPointerException due to an invalid view ID. Also, when a view is only     exists regarding some conditions, the field containing its reference in the binding class is marked with @Nullable .
 * **Type safety**: All the View binding fields are generated matching the same type as the ones referenced in XML, so there’s no need to typecast. This means that the risk of a class cast exception is much lower, since If for some reason your layout and code doesn’t match, the build will fail at compile time instead at runtime.
 * **Speed**: It doesn't affect build speed because it doesn't use annotation processors. New properties are dynamically created when the first build with view binding is activated. And when you add a new view element to your XML, you don't have to rewrite it every time.
 * **Interoperability**: Generated classes are in Java and are optimized for Kotlin-Java interoperability.
 * **Injection capability**: Generated class can be injected in activity or fragment.
	
```kotlin
abstract class BaseFragment<T : ViewBinding> : Fragment(), Injectable {
    private var _binding: T? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    internal val binding
        get() = _binding as T
	
    ...
   
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = _binding ?: bindingInflater.invoke(inflater, container, false)
        if (activity is HomeActivity) {
            homeActivity = (activity as HomeActivity?)!!
            (activity as HomeActivity).supportActionBar?.hide()
        }

        return requireNotNull(_binding).root
    }
	
    ...
	
    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
	
    ...
}		
```

```kotlin
class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPhotosBinding
        get() = FragmentPhotosBinding::inflate
	
    ...
	
     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
	
	...
	
	binding.rvPhotos.apply {
            val gridManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            }

            adapter = photoAdapter
            photoAdapter?.stateRestorationPolicy = PREVENT_WHEN_EMPTY
            gridManager.spanCount = 1
            gridManager.orientation = resources.configuration.orientation
            itemAnimator?.changeDuration = 0
            addItemDecoration(StaggeredGridItemOffsetDecoration(0, 1), 0)
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            setItemViewCacheSize(RECYCLER_VIEW_CACHE_SIZE)
            isVerticalScrollBarEnabled = false
            layoutManager = gridManager
        }

        binding.swipeRefreshContainer.setOnRefreshListener {
            isFromBackStack = false
            getPhotos()
        }
     }	
}	
```
Please visit the link below if you'd like to dive deep into [View Binding](https://developer.android.com/topic/libraries/view-binding).
	
## Work Manager

WorkManager is an API that makes it easy to schedule reliable, asynchronous tasks that are expected to run even if the app exits or the device restarts. The WorkManager API is a suitable and recommended replacement for all previous Android background scheduling APIs, including FirebaseJobDispatcher, GcmNetworkManager, and Job Scheduler. WorkManager incorporates the features of its predecessors in a modern, consistent API that works back to API level 14 while also being conscious of battery life.
	
What is WorkManager?
WorkManager is one of the Android Architecture Components and part of Android Jetpack, a new and opinionated take on how to build modern Android applications.
It is the current best practice for most background work on Android.
	
```kotlin
class DownLoadPhotoWorker
@AssistedInject
constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    private var downloading = true

    /**
     * Workmanager worker thread which do processing
     * in background, so it will not impact to main thread or UI
     *
     */
    override suspend fun doWork(): Result {
        var isSuccessful = true
        var errorStatus = DownloadManager.ERROR_UNKNOWN

        try {
            withContext(Dispatchers.IO) {
                val downloadManager =
                    context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val url = inputData.getString("url")!!
                val file = File(Environment.DIRECTORY_PICTURES)
                val fileName = url.substring(url.lastIndexOf("/") + 1).take(19)
                val downloadUri = Uri.parse(url)
                val dirType = file.toString()
                val request = DownloadManager.Request(downloadUri).apply {
                    setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(url.substring(url.lastIndexOf("/") + 1))
                        .setDescription("")
                        .setDestinationInExternalFilesDir(context, dirType, "${fileName}.jpg")
                }

                val query = DownloadManager.Query().setFilterById(downloadManager.enqueue(request))

                if (!file.exists()) {
                    file.mkdirs()
                }

                while (downloading) {
                    val cursor: Cursor = downloadManager.query(query)
                    cursor.moveToFirst()
                    when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                        DownloadManager.STATUS_FAILED -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.STATUS_FAILED
                        }

                        DownloadManager.STATUS_PAUSED -> {
                            Timber.d("DownloadManager Status : PAUSED")
                        }

                        DownloadManager.STATUS_PENDING -> {
                            Timber.d("DownloadManager Status : PENDING")
                        }

                        DownloadManager.STATUS_RUNNING -> {
                            Timber.d("DownloadManager Status : RUNNING")
                        }

                        DownloadManager.STATUS_SUCCESSFUL -> {
                            isSuccessful = true
                            downloading = false
                        }

                        DownloadManager.ERROR_UNKNOWN -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.ERROR_UNKNOWN
                        }

                        DownloadManager.ERROR_FILE_ERROR -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.ERROR_FILE_ERROR
                        }

                        DownloadManager.ERROR_INSUFFICIENT_SPACE -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.ERROR_INSUFFICIENT_SPACE
                        }

                        DownloadManager.ERROR_HTTP_DATA_ERROR -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.ERROR_HTTP_DATA_ERROR
                        }

                        DownloadManager.ERROR_UNHANDLED_HTTP_CODE -> {
                            isSuccessful = false
                            downloading = false
                            errorStatus = DownloadManager.ERROR_UNHANDLED_HTTP_CODE
                        }

                        else -> {
                        }
                    }

                    cursor.close()
                }
            }
        } catch (e: Exception) {
            return Result.failure()
        }

        return if (isSuccessful)
            Result.success()
        else
            Result.failure(workDataOf("error" to errorStatus))
    }
}
```

### - WorkManager periodicity

If you want that your work is repeated periodically, you can use a PeriodicWorkRequest.
	
I recommand you to read this [WorkManager periodicity](https://medium.com/androiddevelopers/workmanager-periodicity-ff35185ff006) blog if you'd like to know more about the repeating work in Work Manager.
	
Please visit the link below if you'd like to dive deep into [Work Manager](https://developer.android.com/topic/libraries/architecture/workmanager).
	
Also I recommand you to read [this tech blog](https://medium.com/androiddevelopers/introducing-workmanager-2083bcfc4712) if you'd like to learn more about Work Manager.
	
And please hit this Android [WorkManager meets Kotlin](https://medium.com/androiddevelopers/workmanager-meets-kotlin-b9ad02f7405e) tech blog if you'd like to use Kotlin in Work Manager.

## MVVM with Clean Architecture: A Solid Combination

My purpose with this open-source project was to understand MVVM with Clean Architecture and latest Jetpack libraries, so I skipped over a few things that you can try to improve it further:

Use Kotlin Coroutine and Flow to remove callbacks and make it a little neater.
Use states to represent your UI. (For that, check out this amazing talk by Jake Wharton.)
Use Dagger2 to inject dependencies.

This is one of the best and most scalable architectures for Android apps. I hope you enjoyed this article, and I look forward to hearing how you’ve used this approach in your own apps!

