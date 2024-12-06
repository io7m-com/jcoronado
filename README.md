jcoronado
===

[![Maven Central](https://img.shields.io/maven-central/v/com.io7m.jcoronado/com.io7m.jcoronado.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.io7m.jcoronado%22)
[![Maven Central (snapshot)](https://img.shields.io/nexus/s/com.io7m.jcoronado/com.io7m.jcoronado?server=https%3A%2F%2Fs01.oss.sonatype.org&style=flat-square)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/io7m/jcoronado/)
[![Codecov](https://img.shields.io/codecov/c/github/io7m-com/jcoronado.svg?style=flat-square)](https://codecov.io/gh/io7m-com/jcoronado)
![Java Version](https://img.shields.io/badge/22-java?label=java&color=d4e65c)

![com.io7m.jcoronado](./src/site/resources/jcoronado.jpg?raw=true)

| JVM | Platform | Status |
|-----|----------|--------|
| OpenJDK (Temurin) Current | Linux | [![Build (OpenJDK (Temurin) Current, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcoronado/main.linux.temurin.current.yml)](https://www.github.com/io7m-com/jcoronado/actions?query=workflow%3Amain.linux.temurin.current)|
| OpenJDK (Temurin) LTS | Linux | [![Build (OpenJDK (Temurin) LTS, Linux)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcoronado/main.linux.temurin.lts.yml)](https://www.github.com/io7m-com/jcoronado/actions?query=workflow%3Amain.linux.temurin.lts)|
| OpenJDK (Temurin) Current | Windows | [![Build (OpenJDK (Temurin) Current, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcoronado/main.windows.temurin.current.yml)](https://www.github.com/io7m-com/jcoronado/actions?query=workflow%3Amain.windows.temurin.current)|
| OpenJDK (Temurin) LTS | Windows | [![Build (OpenJDK (Temurin) LTS, Windows)](https://img.shields.io/github/actions/workflow/status/io7m-com/jcoronado/main.windows.temurin.lts.yml)](https://www.github.com/io7m-com/jcoronado/actions?query=workflow%3Amain.windows.temurin.lts)|


## Background

The `jcoronado` package provides a very thin layer over the [Vulkan](https://www.vulkan.org/)
API that intends to provide some degree of memory and type safety. The intention
of the package is to make Vulkan feel like a Java API, without sacrificing
performance. Internally, the package uses the excellent [LWJGL3](https://www.lwjgl.org/)
Vulkan bindings, and adds a thin layer of immutable types and interfaces.

## Features

* Type-safe [Vulkan](https://www.vulkan.org/) frontend
* Strong separation of API and implementation to allow for switching to different bindings at compile-time
* Extensive use of `try-with-resources` to prevent resource leaks
* Strongly-typed interfaces with a heavy emphasis on immutable value types
* Type-safe extension mechanism
* Fully documented (JavaDoc)
* Example code included
* [OSGi](https://www.osgi.org/) ready.
* [JPMS](https://en.wikipedia.org/wiki/Java_Platform_Module_System) ready.
* ISC license

## Requirements

The `jcoronado` package currently targets Vulkan 1.3 and up. Some optional
device features are _required_ by the package.

### synchronization2

The package requires the `synchronization2` feature to be available and enabled.
This is necessary to avoid having a lot of branching code paths around queue
submission and render passes.

At the time of writing, according to the
[Vulkan hardware database](https://vulkan.gpuinfo.org/listdevicescoverage.php?core=1.3&feature=synchronization2&platform=all),
this feature is available on `99.82%` of hardware.

### timelineSemaphore

The package requires the `timelineSemaphore` feature to be available and
enabled. This is necessary because timeline semaphores are a mandatory part
of the API exposed by the package.

At the time of writing, according to the
[Vulkan hardware database](https://vulkan.gpuinfo.org/listdevicescoverage.php?core=1.2&feature=timelineSemaphore&platform=all),
this feature is available on `99.88%` of hardware.

## Building

Install a Vulkan SDK. On Linux, there will be almost certainly be distribution
packages available with names such as `vulkan-validationlayers`, `vulkan-tools`,
etc. On Windows, install the [LunarG SDK](https://vulkan.lunarg.com/). On
Windows, ensure that you have the right vendor drivers installed for your
graphics card; if you don't do this, the library (and the test suite) will
raise exceptions with messages such as "Missing `vulkan-1.dll`".

Then run:

```
$ mvn clean package
```

If this step fails, it's a bug. Please report it!

