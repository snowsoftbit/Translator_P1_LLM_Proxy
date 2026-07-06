// Other people can have other ideas of how to send a request to their
// LlmProxy is our implementation with GWDG/KISS
// this is just the interface
package service;

public interface LlmClient {

    String sendRequest(String text);



}
