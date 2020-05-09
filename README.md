# Test


```json
{
  "list": {
    "elementTypes": [
      "java.lang.String",
      "java.lang.String",
      "java.lang.String"
    ],
    "object": [
      "Java",
      "Python",
      "Go"
    ],
    "primitive": false,
    "primitives": [
      false,
      false,
      false
    ],
    "type": 3
  },
  "list2": {
    "elementTypes": [
      "java.lang.Integer",
      "java.lang.Long",
      "java.lang.Double",
      "java.lang.Boolean"
    ],
    "object": [
      123,
      9223372036854775807,
      123.0,
      true
    ],
    "primitive": false,
    "primitives": [
      false,
      false,
      false,
      false
    ],
    "type": 3
  },
  "set": {
    "elementTypes": [
      "java.lang.String",
      "java.lang.String",
      "java.lang.String"
    ],
    "object": [
      "Java",
      "Go",
      "Python"
    ],
    "primitive": false,
    "primitives": [
      false,
      false,
      false
    ],
    "type": 4
  },
  "user": {
    "className": "org.os.maven.ketty.User",
    "object": {
      "age": 12,
      "bar": {
        "id": 2000110,
        "name": "B-FB"
      },
      "dept": {
        "code": "001",
        "name": "HR"
      },
      "foo": {
        "id": 1000110,
        "name": "F-FB"
      },
      "id": "123456",
      "name": "Ketty"
    },
    "primitive": false,
    "type": 1
  },
  "userArray": {
    "elementTypes": [
      "org.os.maven.ketty.User",
      "org.os.maven.ketty.User"
    ],
    "object": [
      {
        "age": 12,
        "bar": null,
        "dept": {
          "code": "001",
          "name": "HR"
        },
        "foo": null,
        "id": "123456",
        "name": "Ketty"
      },
      {
        "age": 22,
        "bar": null,
        "dept": {
          "code": "008",
          "name": "HR"
        },
        "foo": null,
        "id": "123457",
        "name": "Jack"
      }
    ],
    "primitive": false,
    "primitives": [
      false,
      false
    ],
    "type": 2
  },
  "userArray2": {
    "elementTypes": [
      "java.lang.Integer",
      "java.lang.Long",
      "java.lang.Float",
      "java.lang.Double",
      "java.lang.Boolean",
      "java.lang.Character",
      "org.os.maven.ketty.User"
    ],
    "object": [
      1,
      2,
      3.4,
      4.5,
      true,
      "￿",
      {
        "age": 12,
        "bar": null,
        "dept": {
          "code": "001",
          "name": "HR"
        },
        "foo": null,
        "id": "123456",
        "name": "Ketty"
      }
    ],
    "primitive": false,
    "primitives": [
      false,
      false,
      false,
      false,
      false,
      false,
      false
    ],
    "type": 2
  },
  "userList": {
    "elementTypes": [
      "org.os.maven.ketty.User",
      "org.os.maven.ketty.User"
    ],
    "object": [
      {
        "age": 12,
        "bar": null,
        "dept": {
          "code": "001",
          "name": "HR"
        },
        "foo": null,
        "id": "123456",
        "name": "Ketty"
      },
      {
        "age": 22,
        "bar": null,
        "dept": {
          "code": "008",
          "name": "HR"
        },
        "foo": null,
        "id": "123457",
        "name": "Jack"
      }
    ],
    "primitive": false,
    "primitives": [
      false,
      false
    ],
    "type": 3
  }
}
```


<img src='README/images/JacksonSerializer.png' />