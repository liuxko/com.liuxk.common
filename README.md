

## java 通用类库 

### 使用

##### pom文件中添加对应仓库
    <repositories>
    <repository>
        <id>maven-repo-master</id>
        <url>https://raw.github.com/liuxko/com.liuxk.common/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
    </repositories>
    
##### 添加依赖 
    <dependency>
            <groupId>com.liuxk</groupId>
            <artifactId>common</artifactId>
            <version>1.0.0</version>
        </dependency>