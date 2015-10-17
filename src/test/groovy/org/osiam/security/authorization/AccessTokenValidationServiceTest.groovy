package org.osiam.security.authorization

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.Appender
import ch.qos.logback.core.spi.AppenderAttachable
import org.osiam.client.OsiamConnector
import org.osiam.client.exception.ConnectionInitializationException
import org.osiam.client.exception.UnauthorizedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException
import spock.lang.Specification

class AccessTokenValidationServiceTest extends Specification {

    public static final String IRRELEVANT = 'irrelevant'
    public static final String MESSAGE = 'message'

    def appender = Mock(Appender)
    def rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)
    def connector = Mock(OsiamConnector)

    def accessTokenValidator = new AccessTokenValidationService(IRRELEVANT)

    def setup() {
        ((AppenderAttachable<ILoggingEvent>) rootLogger).addAppender(appender)
        accessTokenValidator.connector = connector
    }

    def 'failure to connect to auth-server when revoking an access token is logged'() {
        when:
        accessTokenValidator.revokeAccessTokens(IRRELEVANT, IRRELEVANT)

        then:
        1 * connector.revokeAllAccessTokens(_, _) >> { throw new ConnectionInitializationException(MESSAGE) }
        1 * appender.doAppend({
            it.message == "Unable to revoke access token on auth-server $IRRELEVANT: $MESSAGE"
        })
        thrown(ConnectionInitializationException)
    }

    def 'failure to connect to auth-server when validating an access token is logged'() {
        when:
        accessTokenValidator.loadAuthentication(IRRELEVANT)

        then:
        1 * connector.validateAccessToken(_) >> { throw new ConnectionInitializationException(MESSAGE) }
        1 * appender.doAppend({
            it.message == "Unable to retrieve access token from auth-server $IRRELEVANT: $MESSAGE"
        })
        thrown(ConnectionInitializationException)
    }

    def 'unauthorized to auth-server when validating an access token is logged'() {
        when:
        accessTokenValidator.loadAuthentication(IRRELEVANT)

        then:
        1 * connector.validateAccessToken(_) >> { throw new UnauthorizedException(MESSAGE) }
        1 * appender.doAppend({
            it.message == "Unable to retrieve access token from auth-server: $MESSAGE"
        })
        thrown(InvalidTokenException)
    }

    def 'unauthorized to auth-server when revoking an access token is logged'() {
        when:
        accessTokenValidator.revokeAccessTokens(IRRELEVANT, IRRELEVANT)

        then:
        1 * connector.revokeAllAccessTokens(_, _) >> { throw new UnauthorizedException(MESSAGE) }
        1 * appender.doAppend({
            it.message == "Error revoking all access tokens on auth-server: $MESSAGE"
        })
        thrown(InvalidTokenException)
    }
}
