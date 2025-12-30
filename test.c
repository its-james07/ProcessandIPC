#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
int main() {
    pid_t pid = fork();
    if (pid < 0){
        exit(1);       
    }
    if (pid > 0){
        exit(0);      
    }
    setsid();          
    chdir("/");        
    umask(0);          

    int fd = open("/dev/null", O_RDWR);
    dup2(fd, STDIN_FILENO);
    dup2(fd, STDOUT_FILENO);
    dup2(fd, STDERR_FILENO);
    close(fd);

    FILE *log = fopen("/tmp/daemon.log", "a");
    while (1) {
        fprintf(log, "Daemon running... PID=%d\n", getpid());
        fflush(log);  
        sleep(10);
    }
    fclose(log);
    return 0;
}