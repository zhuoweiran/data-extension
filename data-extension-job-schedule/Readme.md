## data-extension-job-schedule

## 启动方法
```
nohup java -jar data-extension-job-schedule-1.0-SNAPSHOT.jar --spring.profiles.active = ${env} &
```
env 有三种:
- dev 开发环境
- prod 生产环境
- test 测试环境