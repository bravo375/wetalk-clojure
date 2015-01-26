@Grab(group='com.twilio.sdk', module='twilio-java-sdk', version='3.4.6')
import com.twilio.sdk.TwilioRestClient
import org.apache.http.message.BasicNameValuePair

// Find your Account Sid and Token at twilio.com/user/account
ACCOUNT_SID = 'ACbee9cae6cca2b82845e057571236378e'
AUTH_TOKEN = '811da513032f1f8866e9bcb127b167cd'

client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN)

// Build a filter for the MessageList
params = []
params << new BasicNameValuePair("Body", "Hey!")
params << new BasicNameValuePair("To", "+15005550006")
params << new BasicNameValuePair("From", "+15005550006")

message = client.getAccount().getMessageFactory().create(params)
println message.getSid()
