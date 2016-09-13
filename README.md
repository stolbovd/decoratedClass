# decoratedClass
Example of question: how to exclude **decoratedClass** from JSON links, created from Projection and received with Spring @RestController

https://jira.spring.io/browse/DATAREST-890
https://jira.spring.io/browse/DATACMNS-909

- [x] **Resolved by Oliver Gierke @olivergierke in `spring-data-commons 1.13.0.BUILD-SNAPSHOT`**

Experiment with spring-boot version: in 1.3.7.RELEASE (spring data 2.4.4) decoratedClass is absent, but in 1.4.0.RELEASE (spring data 2.5.2) it is present

I'd like get clean JSON, when using Projection as DTO with RestController.
However, in the best case we get JSON, most of which contain links to dekoratedClass.
`{"name":"Dmitry","id":1,"decoratedClass":"ru.inkontext.domain.Person",
"adress":{"id":1,"decoratedClass":"ru.inkontext.domain.Adress","city":"Surgut"}}
`

Tests below imaging matter of question.

## RestController
### Without Projection
`mockMvc.perform(get("/rest/persons/1"))
    .andExpect(jsonPath("id").value(1))
    .andExpect(jsonPath("decoratedClass").doesNotExist());
`

### Projection created by Repository
`mockMvc.perform(get("/rest/persons/1/projected"))
  .andExpect(jsonPath("id").value(1))
  .andExpect(jsonPath("decoratedClass").value("java.util.HashMap"));
`

### Projection created by Repository with Generic
`mockMvc.perform(get("/rest/persons/1/projectedClass"))
  .andExpect(jsonPath("id").value(1))
  .andExpect(jsonPath("decoratedClass").value("java.util.HashMap"));
`
### Projection created by Projection Factory
`mockMvc.perform(get("/rest/persons/1/adressCity"))
  .andExpect(jsonPath("id").value(1))
  .andExpect(jsonPath("decoratedClass").value("ru.inkontext.domain.Person"));
`
## Standart Sring Data REST API
### without Projection
`mockMvc.perform(get("/rest/api/persons/1"))
  .andExpect(jsonPath("id").doesNotExist())
  .andExpect(jsonPath("_links").exists());
`
### with Projection
`mockMvc.perform(get("/rest/api/persons/1?projection=adressCity"))
    .andExpect(status().isOk())
    .andExpect(jsonPath("id").value(1)) 
    .andExpect(jsonPath("_links").exists());
`
