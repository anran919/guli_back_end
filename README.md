#### Redis ###
[redis参考连接](https://blog.csdn.net/qq_45220508/article/details/122980040)

macOs 安装```brew install redis```

启动:```brew services start redis```

重启:```brew services restart redis```

图形化界面: ```redis-cli```

配置文件位置: ```/usr/local/etc/redis.conf```

卸载: ```brew uninstall redis rm ~/Library/LaunchAgents/homebrew.mxcl.redis.plist```

配置:
```yaml
  redis:
    port: 6379
    database: 0
    lettuce:
      shutdown-timeout: 1800000
      pool:
        max-active: 20
        max-wait: -1   #最大阻塞等待时间(负数表示没限制)
        max-idle: 8
        min-idle: 0
    host: 127.0.0.1
```