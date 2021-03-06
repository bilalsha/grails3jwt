package net.kaleidos.grails.plugin.security.stateless.provider

import test.TestUser
import spock.lang.Specification
import grails.transaction.Rollback
import grails.test.mixin.integration.Integration

@Rollback
@Integration
class UserSaltProviderSpec extends Specification {
    void "Retrieve user salt"() {
        setup:
            def provider = new UserSaltProvider()
            provider.init(TestUser, "username", "salt")

            new TestUser(username: username, salt: salt).save()

        when:
            def result = provider.getUserSalt(username)

        then:
            result == salt

        where:
            username = "test1"
            salt = "salt"
    }

    void "Retrieve user salt (user doesn't exist')"() {
        setup:
            def provider = new UserSaltProvider()
            provider.init(TestUser, "username", "salt")

        when:
            def result = provider.getUserSalt(username)

        then:
            result == null

        where:
            username = "test2"
            salt = "salt"
    }

    void "Update user salt"() {
        setup:
            def provider = new UserSaltProvider()
            provider.init(TestUser, "username", "salt")

            new TestUser(username: username, salt: salt).save()

        when:
            provider.updateUserSalt(username, newSalt)
            def result = TestUser.findByUsername(username)

        then:
            result.salt == newSalt

        where:
            username = "test3"
            salt = "salt"
            newSalt = "newSalt"
    }
}
