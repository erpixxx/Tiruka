# Tiruka
A modern, highly configurable, and feature-rich multi-purpose Discord bot designed to enhance server management, automation, and engagement.

> [!IMPORTANT]  
> Tiruka is currently in active development and not yet ready for production use. This repository serves as a development preview.

## 🧩 Bot features
- **Economy**
    - Virtual currency system
    - Daily/Weekly/Monthly rewards
    - Customizable rewards
- **Moderation**
    - User management (ban, kick, mute)
    - Logging and audit trails
    - Customizable moderation commands
- **Leveling**
    - XP system for chatting and activity on voice channels
    - Customizable XP rewards
- **Fun**
    - Games and mini-games
- **Utility**
    - Server management tools
    - Dynamic voice channels
    - Customizable welcome/leave messages
    - Customizable bot status

## 🚀 Core Architecture
- **Docker support**
    - Docker Compose configuration for local development
- **Multi-Level Caching System**
    - Hybrid Memory → Redis → Database caching strategy
    - Configurable TTL policies for each layer
    - Write-through cache update strategy
- **Multi-Instance Support** 
    - Redis-backed cluster coordination
    - Horizontal scaling capabilities
- **Database Agnostic Design**
    - Supports H2, SQLite, MySQL, MariaDB, PostgreSQL
    - Unified storage API with automatic schema management
- **Sharding-Ready Infrastructure**
    - Automatic shard management
    - Graceful shard recovery
- **Hybrid Command System** 
    - Unified architecture for Discord and console commands
    - Dynamic command tree with nested arguments
- **Advanced Console Interface**
    - Colored output with ANSI support
    - Interactive command prompt
- **Custom classloader for dependency injection**
- **Comprehensive Metrics**
    - Integrated Prometheus endpoint
    - Real-time performance monitoring
    - JVM metrics collection
- **Easy configuration**
    - YAML-based configuration files
    - Environment variable support

And much more which I probably forgot to mention here.


> [!NOTE]
> This project is in active development and not yet ready for production use. The features and architecture may change significantly in the future.
> If you wish to suggest a feature or report a bug, please open an issue on GitHub.
