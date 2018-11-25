# Ehcache

build.gradle

```markdown
dependencies {
    compile('org.springframework.boot:spring-boot-starter-cache')
    compile('org.springframework.boot:spring-boot-starter-web')

    compile group: 'net.sf.ehcache', name: 'ehcache', version: '2.10.3'

    testCompile('org.springframework.boot:spring-boot-starter-test')
}
```

spring-boot-starter-cache는 캐시관련 설정을 편리하게 해준다.
CacheManager, EhCacheManagerFactoryBean 등의 bean 생성을 자동화 해준다.
 
resources/ehcache.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
    updateCheck="false">
    <diskStore path="java.io.tmpdir" />

    <cache name="findMemberCache"
           maxEntriesLocalHeap="10000"
           maxEntriesLocalDisk="1000"
           eternal="false"
           diskSpoolBufferSizeMB="20"
           timeToIdleSeconds="300" timeToLiveSeconds="600"
           memoryStoreEvictionPolicy="LFU"
           transactionalMode="off">
        <persistence strategy="localTempSwap" />
    </cache>

</ehcache>
```

ehcache 사용을 위해 필수로 등록해야하는 xml이다. <b>name</b>을 통해 캐싱을 적용할 메서드를 선택할 수 있다.

Application.java

```java
@EnableCaching
```

@EnableCaching을 통해 @Cacheable, @CacheEvict와 같은 어노테이션들을 사용할 수 있다.

```java
    @Override
    @Cacheable(value="findMemberCache", key="#name")
    public Member findByNameCache(String name) {
        slowQuery(2000);
        return new Member(0, name+ "@gmail.com", name);
    }

    @Override
    @CacheEvict(value = "findMemberCache", key="#name")
    public void refresh(String name) {
        logger.info(name + "의 Cache Clear!");
    }
```

@Cacheable 어노테이션을 통해 ehcache.xml의 name이 findMemberCache로 등록되어 있는 캐시 설정을 사용
할 수 있다. <strong>key</strong>에 따라 별도의 캐싱을 적용하겠다는 뜻이다.

또한, @CacheEvict를 통해 캐싱을 깨버릴 수 있다.

### 캐싱 적용 결과

![](https://img1.daumcdn.net/thumb/R1920x0/?fname=http%3A%2F%2Fcfile9.uf.tistory.com%2Fimage%2F254E7D5058134DA31D9806)

캐싱이 적용되어 저장되어 있던 값이 반환되는 것을 확인할 수 있다.

### 참조

https://jojoldu.tistory.com/57#at_pco=smlwn-1.0&at_si=5bfa24f558ee2487&at_ab=per-2&at_pos=0&at_tot=1