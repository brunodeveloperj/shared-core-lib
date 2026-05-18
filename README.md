# shared-core-lib

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.6-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3.9-blue)
![Status](https://img.shields.io/badge/status-active-success)

A foundational Spring Boot library providing reusable utility classes, structural patterns, and shared methodologies for consistent application development.

## Overview

This library serves as the foundation of the MDS ecosystem by centralizing reusable resources and common abstractions shared across all MDS libraries and microservices.

It reduces duplication and promotes standardized engineering practices.

---

## Features

- **Functional Execution Pattern** — `FunctionUtils` with sync/async, null-safe, and exception-customizable execution
- **Async Join** — `ExecutableFuture` for parallel `CompletableFuture` orchestration
- **Type Conversions** — `ConversionHelper` for Object ↔ String/Long/Double/Integer/Boolean/LocalDateTime/JSON/Base64
- **Gson Adapters** — `LocalDateTypeAdapter`, `LocalDateTimeTypeAdapter` for Java Time serialization
- **Reflection Utilities** — `ReflectionUtils` for field access, caching, and accessibility
- **Object Utilities** — `ObjectUtils` with null checks, string helpers, format, and annotation-based field cleaning
- **Collection Utilities** — `CollectionUtils` for common collection operations
- **Generic Page Response** — `GenericPageResponse<T>` record wrapping Spring Data `Page` for API responses
- **Cache Control** — `CacheControlUtil` for no-cache response headers
- **Custom Annotations** — `@RemoveSpecialCharacters` for field-level sanitization
- **Constants** — `UtilKeys`, `PatternUtilsKeys` for shared constants

---

## Installation

```xml
<dependency>
    <groupId>com.mds</groupId>
    <artifactId>shared-core-lib</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

---

## Project Structure

```text
com.mds.shared.core
├── adapter/           — Gson type adapters (LocalDate, LocalDateTime)
├── base/              — GenericPageResponse record
├── cache/             — CacheControlUtil
├── helper/            — ConversionHelper
├── keys/              — UtilKeys constants
└── pattern/
    ├── annotation/    — @RemoveSpecialCharacters
    ├── base/          — AbstractFunctionBase
    ├── entity/        — Breaker, ExecutableFuture, ObjectNullSafe
    ├── exception/     — ExecutableException
    ├── interfaces/    — ExecutablePatternObject, ExecutablePatternVoid, SupplierPattern*
    ├── keys/          — PatternUtilsKeys
    └── utils/         — FunctionUtils, ObjectUtils, ReflectionUtils, CollectionUtils
```

---

## Roadmap

- Metrics support
- Event abstractions
- Shared tracing resources

---

## Author

Martins Desenvolvimento de Sistemas (MDS)
