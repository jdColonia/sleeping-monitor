# El Monitor Dormilón

## **Integrantes:**

- Santiago Escobar León - A00382203
- Juan Manuel Díaz Moreno - A00394477
- Juan David Colonia Aldana - A00395956

## Problema

El departamento de CSI de la Universidad Icesi dispone de un monitor que ayuda a los estudiantes de los cursos de algoritmos con sus tareas de programación. La oficina del monitor es pequeña y solo tiene espacio para un escritorio, una silla para el monitor, una silla de visita y tres sillas en el corredor, donde los estudiantes pueden esperar si el monitor está ocupado.

El comportamiento del sistema es el siguiente:

- Si no hay estudiantes, el monitor duerme.
- Si un estudiante llega y el monitor está dormido, lo despierta para ser atendido.
- Si el monitor está ocupado, el estudiante se sienta en una silla a esperar.
- Si no hay sillas disponibles, el estudiante se va a programar a la sala de cómputo y regresa más tarde.
- El monitor atiende a los estudiantes en orden de llegada. Si no hay estudiantes en espera, vuelve a dormirse.

Se debe implementar una solución en Java utilizando hilos y semáforos para coordinar la concurrencia de los estudiantes y el monitor.

## Solución

La solución se basa en el uso de hilos para representar a los estudiantes y al monitor. Para la sincronización, se utilizan semáforos que controlan:

- La cantidad de sillas disponibles.
- El acceso al monitor.
- El despertar del monitor cuando llega un estudiante.
- La atención de los estudiantes en orden de llegada.

Se utiliza una cola de espera para asegurar que los estudiantes sean atendidos en el orden correcto.

## Explicación del Código

### 1. Gestión de Semáforos (SemaphoreManager)

Se implementa una clase `SemaphoreManager` que centraliza los semáforos:

- `monitorSemaphore`: Despierta al monitor cuando un estudiante llega.
- `mutexSemaphore`: Garantiza acceso exclusivo a la lista de espera.
- `seatSemaphore`: Controla la cantidad de sillas disponibles.
- `studentReadySemaphore`: Sincroniza la atención de los estudiantes.
- `helpCompletedSemaphore`: Asegura que la asistencia se complete antes de que el estudiante se retire.

### 2. Clase Estudiante (Runnable)

Cada estudiante alterna entre programar y buscar ayuda:

- Programa durante un tiempo aleatorio.
- Busca ayuda del monitor:
  - Si hay sillas disponibles, se sienta y espera su turno.
  - Si no hay sillas, se va a programar y regresa después.
  - Si el monitor está dormido, lo despierta.

### 3. Clase Monitor (Runnable)

El monitor:

- Duerme hasta que un estudiante lo despierte.
- Atiende a los estudiantes en orden:
  - Verifica si hay estudiantes en la cola.
  - Atiende a un estudiante y lo elimina de la cola.
  - Si no hay estudiantes, vuelve a dormir.

### 4. Control de Flujo con Semáforos

- `monitorSemaphore.acquire()`: El monitor espera hasta que un estudiante lo despierte.
- `mutexSemaphore.acquire() / release()`: Controla el acceso seguro a la cola de espera.
- `seatSemaphore.tryAcquire()`: Un estudiante se sienta si hay espacio.
- `studentReadySemaphore.release()`: Notifica a un estudiante que está siendo atendido.
- `helpCompletedSemaphore.acquire()`: Espera hasta que la asistencia finalice.

## Conclusiones

- **Gestión de concurrencia**: Aprendimos cómo manejar procesos concurrentes usando semáforos para coordinar la interacción entre hilos de manera eficiente y evitar condiciones de carrera.
- **Uso de estructuras de datos sincronizadas**: Implementamos una cola de espera con una estructura de datos segura para concurrencia, garantizando que los estudiantes sean atendidos en el orden correcto.
- **Modularización del código**: Separar la lógica en clases específicas (`Monitor`, `Estudiante`, `SemaphoreManager`) mejoró la organización, la reutilizabilidad y la mantenibilidad del código.
- **Simulación de sistemas reales**: La implementación de tiempos de espera aleatorios para la programación y la asistencia hizo que la simulación fuera más realista y representativa de un entorno con concurrencia.
- **Importancia del control de acceso**: Aprendimos a gestionar el acceso exclusivo a recursos compartidos mediante semáforos, evitando bloqueos innecesarios y asegurando que el sistema funcione de manera ordenada y sin errores.
