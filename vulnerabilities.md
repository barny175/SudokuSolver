```
#include <stdio.h>
#include <stdlib.h>
#include <wchar.h>
#define PASSWORD "ABCD1234!"
/*You need not worry about other include statements if at all any are missing */
void func1()
{
      char * data;
      char * dataBuffer = (char *)ALLOCA(100*sizeof(char));
      memset(dataBuffer, 'A', 100-1);
      dataBuffer[100-1] = '\0';
      
/***data points to the memory which was not allocated by the program ***/
      `data = dataBuffer - 8;` 
      {
            char source[100];
            memset(source, 'C', 100-1);
            source[100-1] = '\0';

/*** writing to memory which was not allocated ***/ 
            strcpy(data, source);
            if(data != NULL)
            {
                printf("%s\n", data);
            }
      }
}

/*** memory allocated via calloc is never freed causing memory leak ***/ 
void func2()
{
    char * data;
    data = NULL;
    data = (char *)calloc(100, sizeof(char));
    strcpy(data, "A String");
    if(data != NULL)
    {
        printf("%s\n", data);
    }
}

/*** In general using strcpy could cause vulnerability because it does not check the length of string
 * and therefore it can overwrite contiguous to intended destination
 * but in this case the passwordBuffer is big enough for PASSWORD
 * so I don't see any problem in this function
***/
void func3()
{
    char * password;
    char passwordBuffer[100] = "";
    password = passwordBuffer;
    strcpy(password, PASSWORD);
    {
        HANDLE pHandle;
        char * username = "User";
        char * domain = "Domain";
        /* Let's say LogonUserA is a custon authentication function*/
        if (LogonUserA(
                    username,
                    domain,
                    password,
                    &pHandle) != 0)
        {
            printf("User logged in successfully.\n");
            CloseHandle(pHandle);
        }
        else
        {
           printf("Unable to login.\n");
        }
    }
}

/***
 * similar to func3() potentially dangerous strcpy is used but the allocated memory is big enough for the given string
 ***/
static void func4()
{
    char * data;
    data = NULL;
    data = (char *)calloc(20, sizeof(char));
    if (data != NULL)
    {
        strcpy(data, "Initialize");
        if(data != NULL)
        {
            printf("%s\n", data);
        }
        free(data);
    }
}

void func5()
{
    int i = 0;
    do
    {
        printf("%d\n", i);
/*** i will always be less then 256 and greater or equal to 0 ***/
        i = (i + 1) % 256;

/*** condition is always fulfilled => infinite loop ***/
    } while(i >= 0);
}

/***
 * Using fgets is safe as opposed to gets because it check the length of the input
 * so I don't see any problem in the function
 ***
void func6()
{
    char dataBuffer[100] = "";
    char * data = dataBuffer;
    printf("Please enter a string: ");
    if (fgets(data, 100, stdin) < 0)
    {
        printf("fgets failed!\n");
        exit(1);
    }
    if(data != NULL)
    {
        printf("%s\n", data);
    }
}

void func7()
{
    char * data;
    data = "Fortify";
    data = NULL;
/*** equivalent to printf("%s\n", NULL) which has undefined behaviour ***/
    printf("%s\n", data);
}

int main(int argc, char * argv[])
{
    printf("Calling func1\n");
    func1();
    printf("Calling func2\n");
    func2();
    printf("Calling func3\n");
    func3();
    printf("Calling func4\n");
    func4();
    printf("Calling func5\n");
    func5();
    printf("Calling func6\n");
    func6();
    printf("Calling func7\n");
    func7();
    return 0;
}
```