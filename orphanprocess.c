#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>
#include<sys/types.h>
int main(){
    pid_t pid = fork();
    if(pid > 0){
        printf("Ma Parent Process | Mero Id: %d| Terminating..\n",getpid());
        exit(0);
    }else if(pid == 0){
        sleep(5);
        printf("\nMa Orphaned Child Process | Mero Id:%d| Parent Id:%d | Adopted by init\n", getpid(), getppid());

    }else{
        perror("Failed to fork");
        exit(1);
    }
    return 0;
}