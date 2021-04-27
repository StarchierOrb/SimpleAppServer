# SmartApp-Server
SmartApp的服务端程序，用于处理用户数据、通过socket与安装客户端连接。
代码是一座大屎山，估计人多性能会暴毙。
也许有哪个高人学习了算法和架构的帮忙改进一下。
也许以后会移植到其他语言运行。(x)

# 构建
使用以下指令进行构建:
`gradlew incrementBuildNumber shadowJar --warning-mode all -Dfile.encoding=UTF-8`

# 运行

运行环境： JDK11
命令行运行指令：`java -Dlog4j.skipJansi=false -jar smartapp-server-xxx-SNAPSHOT-all.jar`
xxx为版本号
