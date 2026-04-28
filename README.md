# Sistema Web de Control de Inventarios con Alertas Inteligentes

![PHP](https://img.shields.io/badge/PHP-8.x-blue)
![Laravel](https://img.shields.io/badge/Laravel-11-red)
![Python](https://img.shields.io/badge/Python-3.11-yellow)
![FastAPI](https://img.shields.io/badge/FastAPI-IA-green)
![MySQL](https://img.shields.io/badge/MySQL-8-orange)
![Estado](https://img.shields.io/badge/Estado-En%20Desarrollo-blue)

---

## Descripción General

Sistema web desarrollado para optimizar la gestión de inventarios en entornos gastronómicos, implementado como solución tecnológica para el restaurante CLUB GRILL.

El sistema integra monitoreo en tiempo real, automatización de procesos y un módulo de inteligencia artificial capaz de predecir el consumo de insumos, permitiendo anticipar escenarios de desabastecimiento.

---

## Problemática

El proceso actual de gestión de inventarios presenta las siguientes limitaciones:

- Registros manuales propensos a errores  
- Información desactualizada del stock  
- Falta de alertas ante niveles críticos  
- Pérdidas por desabastecimiento inesperado  
- Toma de decisiones basada en datos no confiables  

---

## Solución Propuesta

Se implementa una plataforma web centralizada que permite:

- Control en tiempo real del inventario  
- Registro estructurado de movimientos (entradas y salidas)  
- Generación automática de alertas  
- Predicción del consumo mediante modelos de Machine Learning  
- Gestión de usuarios con control de roles  

---

## Arquitectura del Sistema

### Vista General

```plaintext
[ Cliente Web ]
        ↓
[ Frontend - Blade + Tailwind ]
        ↓
[ Backend - Laravel (API REST) ]
        ↓
[ Base de Datos - MySQL ]
        ↓
[ Microservicio IA - FastAPI ]
        ↓
[ Firebase Cloud Messaging ]
```

---

## Arquitectura de Inteligencia Artificial

El sistema incorpora un microservicio independiente encargado del análisis predictivo del consumo de insumos.

### Flujo de funcionamiento

1. Recolección de datos históricos desde la base de datos  
2. Procesamiento y limpieza de datos con Pandas  
3. Entrenamiento del modelo predictivo  
4. Generación de predicciones de consumo  
5. Envío de resultados al backend Laravel  
6. Activación de alertas inteligentes  

### Variables consideradas

- Historial de consumo  
- Frecuencia de uso de insumos  
- Tendencias temporales  
- Stock actual disponible  

### Tecnologías utilizadas

- FastAPI  
- Scikit-learn  
- Pandas  

---

## Tecnologías Utilizadas

### Backend
- PHP 8  
- Laravel 11  

### Frontend
- Blade Templates  
- Tailwind CSS  

### Base de Datos
- MySQL 8  

### Inteligencia Artificial
- Python 3.11  
- FastAPI  
- Scikit-learn  
- Pandas  

### Notificaciones
- Firebase Cloud Messaging  

---

## Estructura del Proyecto

```plaintext
sistema-inventario/
├── backend/
├── frontend/
├── ai-service/
├── database/
├── docs/
├── images/
├── README.md
└── .gitignore
```

---

## Instalación

### Requisitos

- PHP >= 8  
- Composer  
- Node.js y npm  
- MySQL  
- Python 3.11  

---

### Clonar repositorio

```bash
git clone https://github.com/tu-usuario/sistema-inventario.git
cd sistema-inventario
```

---

### Configuración Backend

```bash
cd backend
composer install
cp .env.example .env
php artisan key:generate
```

Configurar `.env`:

```env
DB_DATABASE=nombre_db
DB_USERNAME=root
DB_PASSWORD=
```

```bash
php artisan migrate
php artisan serve
```

---

### Configuración Frontend

```bash
npm install
npm run dev
```

---

### Configuración Microservicio IA

```bash
cd ai-service
pip install -r requirements.txt
uvicorn main:app --reload
```

---

## Uso del Sistema

- Registrar insumos  
- Actualizar stock en tiempo real  
- Registrar movimientos  
- Visualizar inventario  
- Recibir alertas automáticas  
- Consultar predicciones de consumo  

---

## API (Resumen)

### Obtener inventario
GET /api/inventario

### Registrar movimiento
POST /api/movimientos

### Obtener alertas
GET /api/alertas

---

## Metodología

Desarrollo basado en Extreme Programming (XP):

- Desarrollo iterativo  
- Entregas incrementales  
- Validación continua  

---

## Resultados Esperados

- Reducción de errores en inventarios  
- Mejora en la eficiencia operativa  
- Disminución de pérdidas  
- Mejor toma de decisiones  

---

## Autor

Brayan Diego Mamani Huanca  
Ingeniería de Sistemas  
Universidad Salesiana de Bolivia  

---

## Licencia

Proyecto de carácter académico.
