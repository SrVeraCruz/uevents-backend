# UEvents - my event is yours
## UEvents Backend API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSql](https://img.shields.io/badge/PostgreSql-0d7ebf.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)

This project is an API built using **Java, Java Spring, AWS Relational Database Service (RDS) PostgreSql, 
AWS Virtual Private Network (VPC), AWS Elatic Compute Cloud (EC2), AWS Simple Storage Service (S3).**

The service was developed to create an API that allows you to manage events, 
allowing you to register them, filter and detail events, 
as well as the association of discount coupons.

The application was designed to be deployed on **AWS**, using an **EC2** instance, 
which communicates with a **PostgreSQL** instance also deployed on **AWS**. 
All communication is managed by a **VPC** that is divided into two parts: 
1. **Public network containing EC2**
2. **Private network containing the RDS Postgres instance** 

It is also important to note that the event files are managed by **AWS S3**.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Deploy](#deploy)
- [Contributing](#contributing)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/SrVeraCruz/uevents-backend.git
```

2. Install dependencies with Maven.
3. Create a configuration with your runtime environment variables with your 
AWS Credentials that are used in `application.properties` and provide de credentials 
on the AWSConfig class.

```yaml
aws.region=us-east-1
aws.accessKeyId=${AWS_KEY_ID}
aws.secretKey=${AWS_SECRET}
aws.bucket.name=${AWS_S3_BUCKET_NAME}
```

or configure locally the **AWS CLI** in your machine

**Config Values**

```yaml
AWS_KEY_ID=VALUE;AWS_SECRET=VALUE2
```

**PostgreSql**

1. Create a DB for postgres using pgAdmin 4: http://localhost:5432
2. Log with admin:pass and create a database called 'uevents'
3. Create a configuration with your runtime environment variables with your
   postgres Database Credentials that are used in `application.properties`.

```yaml
spring.datasource.url=jdbc:postgresql://localhost:5432/uevents
spring.datasource.username=${POSTGRES_USERNAME}
spring.datasource.password=${POSTGRES_PASSWORD}
```

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8090

## API Endpoints
The API provides the following endpoints:

**API Event**
```markdown
POST /api/event - Create a new event
GET  /api/event/{id} - Retrieve one event details
GET  /api/event - Retrieve all events
GET  /api/event/filter - Retrieve all events according to filters
```

**BODY**
```json
{
  "title": "Bootcamp IT",
  "description": "Informatique Bootcamp",
  "image": "(image: multipatFileType)",
  "eventUrl": "http://www.exemple.com",
  "remote": false,
  "date": "1848544816760",
  "country": "Portugal",
  "city": "Lisbon",
  "state": "Lisbon"
}
```

**API Coupon**
```markdown
POST  /api/coupon/event/{id} - Create and add a new coupon to event
```

**BODY**
```json
{
  "code" : "vera-prime22-2025",
  "discount" : 22,
  "validUntil" : 1848544816760
}
```

## Deploy

**VPC**

1. Create a VPC on AWS and subdivide on public and private network.
2. Configure the internet Gateway.

**EC2**

1. Create a EC2 instance with a JRE.
2. Associate to your VPC public network.
3. Upload project file via ssh.
4. Run the application.

**RDS Postgres**

1. Create a RDS Postgres instance.
2. Create a db called 'uevents'.
3. You should configure your runtime environment variables with your
      RDS Postgres Database Credentials that are used in `application.properties`.

```yaml
spring.datasource.url=${AWS_RDS_PG_URL}
spring.datasource.username=${AWS_RDS_PG_USERNAME}
spring.datasource.password=${AWS_RDS_PG_PASSWORD}
```

or grant RDS management permission on your EC2 instance.

**S3**

1. Create an S3 bucket called 'uevents-upload'.
2. Define the public access policy.

```yaml
{
  "Version": "2012-10-17",
  "Id": "Policy1738209765832",
  "Statement": [
    {
      "Sid": "PublicReadGetObject",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::uevents-upload/*"
    }
  ]
}
```

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.