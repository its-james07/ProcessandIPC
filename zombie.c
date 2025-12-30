#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
    pid_t pid = fork();
    if (pid > 0) {
        printf("Parent Process | Id: %d\n", getpid());
        sleep(20); 
        
    } else if (pid == 0) {
        printf("Child Process | Id: %d\n", getpid());
        printf("If parent doesn't wait for me, I will be a zombie.\n");
        return 0;  
    } else {
        perror("Failed to fork!");
        return -1;
    }
    return 0;
}