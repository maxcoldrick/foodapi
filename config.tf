provider "kubernetes" {}

resource "kubernetes_pod" "web" {
  metadata {
    name = "web"
    labels = {
      app = "web"
    }
  }

  spec {
    container {
      image = "maxcoldrick/foodapi"
      name  = "web"
      args = ["puma"]
      command=["migrate/dbmigrate.sh"]
      working_dir="/app"
      env {
        name = "DATABASE_URL"
        value = "postgres://postgres@db"
      }
      port {
        container_port = 3000
      }
    }
    restart_policy = "Always"
  }
}

resource "kubernetes_service" "web" {
  metadata {
    name = "web"
  }
  spec {
    selector = {
      app = "${kubernetes_pod.web.metadata.0.labels.app}"
    }
    port {
      name        = "3000"
      port        = 3000
      target_port = 3000
    }
  }
}

resource "kubernetes_pod" "db" {
  metadata {
    name = "db"
    labels = {
      app = "db"
    }
  }

  spec {
    container {
      image = "postgres:10.3-alpine"
      name  = "db"
      port {
        container_port = 5432
      }
    }
    restart_policy = "Always"
  }
}

resource "kubernetes_service" "db" {
  metadata {
    name = "db"
  }
  spec {
    selector = {
      app = "${kubernetes_pod.db.metadata.0.labels.app}"
    }
    port {
      name        = "5432"
      port        = 5432
      target_port = 5432
    }
  }
}

resource "kubernetes_pod" "rabbitmq" {
  metadata {
    name = "rabbitmq"
    labels = {
      app = "rabbitmq"
    }
  }

  spec {
    hostname = "rabbitmq"
    container {
      image = "rabbitmq"
      name  = "rabbitmq"
      port {
        container_port = 15672
      }
      port {
        container_port = 5672
      }
    }
    restart_policy = "Always"
  }
}

resource "kubernetes_service" "rabbitmq" {
  metadata {
    name = "rabbitmq"
  }
  spec {
    selector = {
      app = "${kubernetes_pod.rabbitmq.metadata.0.labels.app}"
    }
    port {
      name        = "7000"
      port        = 7000
      target_port = 15672
    }
    port {
      name        = "7001"
      port        = 7001
      target_port = 5672
    }
  }
}