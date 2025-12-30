#include<stdio.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>
int main(){
    pid_t pid1 = fork();
    if(pid1 < 0){
        printf("Failed to fork");
        return -1;
    }
    else if(pid1 == 0){
        printf("Ma Child Process: | Mero Id: %d | Parent Id: %d\n", getpid(), getppid());
        pid_t pid2 = fork();
        if(pid2 == 0){
            printf("Ma Grandchild Process: | Mero Id: %d | Parent Id: %d ", getpid(), getppid());
            
        }else{
            printf("Grandparent: %d", getppid());
            wait(NULL);
        }
    }else{
        printf("Ma Parent Process | Mero id: %d | Mero Choro: %d\n", getpid(), pid1);
        wait(NULL);
    }
}