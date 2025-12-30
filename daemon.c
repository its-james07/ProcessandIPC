#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
int main() {
    pid_t pid = fork();
    if (pid < 0)
        exit(1);       // Fork failed
    
    if (pid > 0)
        exit(0);       // Parent exits, child continues

    setsid();          // Detach from terminal
    chdir("/");        // Change directory to root
    umask(0);          // Reset file permissions

    // Redirect standard IO to /dev/null
    int fd = open("/dev/null", O_RDWR);
    dup2(fd, STDIN_FILENO);
    dup2(fd, STDOUT_FILENO);
    dup2(fd, STDERR_FILENO);
    close(fd);

    // Daemon work: simple logging every 10 seconds
    FILE *log = fopen("/tmp/daemon.log", "a");
    while (1) {
        fprintf(log, "Daemon running... PID=%d\n", getpid());
        fflush(log);   // Ensure log is written
        sleep(10);
    }
    fclose(log);
    return 0;
}