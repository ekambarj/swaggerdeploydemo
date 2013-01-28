This project is to try out a simple REST API and associate a swagger based documentation to it and test how we can deploy it, associating them together

- Create a simple REST API to visualize the customer data. Implementation can just
  return static values as the key idea is to demonstrate the documentation
  rather than the implementation
- Write the swagger based documentation for the API. More details on swagger
  here, http://developers.helloreverb.com/swagger/
- Create a build script which can build the rest API and also package the
  documentation as part of the deployable
- Build a deployment script which can deploy the REST API on Tomcat and also
  expose the swagger API associated with it and link them together
- Ensure that the build and deployment processes are driven through a CI Job
