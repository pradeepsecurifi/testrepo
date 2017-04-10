#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
void external();
void cwd();
void cd();
void echo_env();
void export_env();
void history();
void fg();
void history_cmd();

char *input;
char **cmdarg;
char **pipearg;
char *pwd;
char **histcmd;

int count;
FILE *fp;
int hist_index=0;

int job=1;
int pid_array[6]={0};

int main(){
	
	//int hist_index=0;
	input=malloc(sizeof(char)*100);//assuming size of total input to be 50

	if(input==NULL){
		printf("Memory cannot be allocated");
		exit(0);
	}
	
	histcmd=malloc(sizeof(char*)*10);
	if(histcmd==NULL){
		printf("Memory cannot be allocated");
		exit(0);
	}
	for(hist_index=0;hist_index<10;hist_index++){
		histcmd[hist_index]=malloc(sizeof(char)*50);

	}
	hist_index=0;

	pwd=malloc(sizeof(char)*50);//assuming size of current working directory to be 40
	
	if(pwd==NULL){
		printf("Memory cannot be allocated");
		exit(0);
	}

	cwd();
	printf("\n");

	fp=fopen("history1","a+");
	if(fp==NULL){
		printf("File cannot be opened\n");
		exit(0);
	}
	
	
	printf("PHANI: %s $:",pwd);
	scanf(" %[^\n]s",input);

	strcpy(histcmd[hist_index],input);
	

	fputs(input,fp);
	fputs("\n",fp);
	fclose(fp);

	while((strcmp(input,"exit"))!=0){

		if(strcmp(input,"history")==0)
			history();

		else if(strstr(input,"fg ")!=NULL)
			fg();

		else if(strstr(input,"!")!=NULL)
			history_cmd();
		
		else
		external();
		cwd();
		printf("\n");
		printf("PHANI: %s $:",pwd);
		
		//malloc all elements again
		input=malloc(sizeof(char)*100);//again assuming input to be of size 50
		if(input==NULL){
			printf("memory cannot be allocated");
			exit(0);
		}
		
		scanf(" %[^\n]s",input);

		fp=fopen("history1","a+");
			if(fp==NULL){
				printf("File cannot be opened\n");
				exit(0);
			}

		fputs(input,fp);
		fputs("\n",fp);
		fclose(fp);

		hist_index++;
		if(hist_index==10)
		hist_index=0;
		strcpy(histcmd[hist_index],input);
		
		

	}

	
	//fclose(fp);
	free(input); //for exit string
	free(pwd);
	return 0;
}

void history_cmd(){
	int i;
	char *temp;
	int flag=0;
	temp=malloc(sizeof(char)*50);
	if(temp==NULL){
		printf("memory cannot be allocated");
		exit(0);
	}
	//printf("entered here to execute previous commands\n");
	strcpy(temp,input);
	temp++;
	for(i=hist_index-1; i>=0 ;i--){
		if((strstr(histcmd[i],temp))!=NULL){
			flag=1;
			break;
		}
	}
	if(flag==0){
		printf("No previous records of the command are found\n");
		return;
	}
	else{
		//printf("trying to copy previous cmd here\n");
		strcpy(input,histcmd[i]);
		//printf("previous command is %s\n",histcmd[i]);
		external();
		
	}	
	
	//free(temp);

	
}



void cwd(){
	pwd=getcwd(pwd,50);
	if(pwd!=NULL){
		//printf("%s",pwd);
	}
	else
	printf("PWD OUTPUT CANNOT BE DEFINED\n");
}


void cd(){
	int ret;
	//printf("argument to cd is %s\n",cmdarg[1]);
	ret=chdir(cmdarg[1]);
	if(ret<0){
		printf("cd : No such File or Directory\n");
		
	}
	//printf("Return value of cd is %d\n",ret);
	//return ret;
}


void echo_env(){
	char *temp;
	char *value;
	
	temp=malloc(sizeof(char)*strlen(cmdarg[1]));
	if(temp==NULL){
		printf("memory cannot be allocated\n");
		exit(0);
	}
	value=malloc(sizeof(char)*100);
	if(value==NULL){
		printf("memory cannot be allocated\n");
		exit(0);
	}
	
	strcpy(temp,cmdarg[1]);
	temp++;
	//printf("Name is %s\n",temp);
	value=getenv(temp);
	if(value==NULL){
		printf("No such environment variable\n");
	}
	else
	printf("%s\n",value);
	temp--;
	free(temp);
}


void export_env(){
	char *name;
	char *value;
	char *temp;
	int ret;
	temp=malloc(sizeof(char)*100);
	if(temp==NULL){
		exit(0);
	}
	name=malloc(sizeof(char)*100);
	if(name==NULL){
		exit(0);
	}
	value=malloc(sizeof(char)*100);
	if(value==NULL){
		exit(0);
	}
	temp=strstr(cmdarg[1],"=");
	temp++;
	strcpy(value,temp);
	temp--;
	*temp='\0';
	strcpy(name,cmdarg[1]);
	//printf("Name is %s value is %s\n",name,value);
	ret=setenv(name,value,1);
	if(ret<0){
		printf("Environment variable not set\n");
	}
	free(name);
	free(value);	
}
void history(){
	strcpy(input,"uniq history1 | cat -n");
	external();
}

void fg(){
	char *jobno;
	int i;
	int status;
	jobno=malloc(sizeof(char)*10);
	if(jobno==NULL){
		printf("Memory cannot be allocated\n");
		return;
	}
	jobno=strstr(input," ");
	jobno++;
	i=atoi(jobno);
	//printf("job no of process in background is %d\n",i);
	if(i<1 || i>5){
		printf("No such job\n");
		return;
	}
	if(pid_array[i]!=0)
	waitpid(pid_array[i],&status,0);
	pid_array[i]=0;
	printf("Background process has been executed\n");
		
	

}


void external(){

	int *pipes;
	int count=0,i,j,k,cmdcount=0,status;
	int ret;

	int inputflag=0;
	FILE *inputfile=NULL;
	int fd;

	int outputflag=0;
	FILE *outputfile=NULL;

	int outputflag_a=0;
	FILE *appendfile=NULL;

	int inputflag_d=0;
	FILE *inputfile_d=NULL;

	int backgroundflag=0;
	int pid;
	

	//printf("ENTER EXTERNAL\n");
	for(i=0;i<strlen(input);i++){
		if(input[i]=='|')
		count++;
	}
	//printf("no of pipes is %d\n",count);
	pipearg=malloc(sizeof(char *)*(count+2));// one for each side of pipe + null
	if(pipearg==NULL){
		printf("memory cannot be allocated\n");
		exit(0);
	}
	for(i=0;i<(count + 2);i++)
	pipearg[i]=malloc(sizeof(char)*10);
	
	i=0;
	pipearg[i]=strtok(input,"|");
	//printf("%s\n",pipearg[i]);
	i++;
	while(1){
		pipearg[i]=strtok(NULL,"|");
		if(pipearg[i]==NULL )
			break;
		//printf("%s\n",pipearg[i]);

		
		
		i++;
		
	}
	if(count>0){
		pipes=malloc(sizeof(int)*2*count);
		if(pipes==NULL){
			printf("Memory cannot be allocated\n");
			exit(0);
		}

		for(i=0;i<2*count;i=i+2){
			ret=pipe(pipes+i);
			if(ret<0)
			printf("pipe not created");
		}
	}

	

	//printf("Individual arguments are\n");
	for(i=0;i<=(count) && pipearg[i]!=NULL ;i++)
	{
		
		inputflag=0;
		inputfile=NULL;

		outputflag=0;
		outputfile=NULL;

		outputflag_a=0;
		appendfile=NULL;

		inputflag_d=0;
		inputfile_d=NULL;

		backgroundflag=0;		

		cmdcount=0;
		for(j=0;j<strlen(pipearg[i]);j++){
			if(pipearg[i][j]==' ')
			cmdcount++;
		}
		cmdarg=malloc(sizeof(char *)*(cmdcount+2));
		for(k=0;k<(cmdcount+2);k++)
			cmdarg[k]=malloc(sizeof(char)*10);
		k=0;
		cmdarg[k]=strtok(pipearg[i]," ");
		//printf("%s\n",cmdarg[k]);
		k++;
		while(1){
			cmdarg[k]=strtok(NULL," ");
			if(cmdarg[k]==NULL)
				break;

			if(strcmp(cmdarg[k],"<")==0)
				inputflag=k;

			if(strcmp(cmdarg[k],">")==0)
				outputflag=k;

			if(strcmp(cmdarg[k],">>")==0)
				outputflag_a=k;

			if(strcmp(cmdarg[k],"<<")==0)
				inputflag_d=k;
			

			//printf("%s\n",cmdarg[k]);
			k++;
		}
		//printf("Am i reaching here %d\n",cmdcount);
		//
		if((cmdcount>1) && (strcmp(cmdarg[k-1],"&")==0)){
			backgroundflag=1;
			cmdarg[cmdcount]=NULL;
			//printf("setting background flag\n");
			
		}
		

		printf("\n");//to flush buffer
		pid=fork();
		if(pid==0){
		
			if(i==0 && count > 0){
				//printf("First Child\n");
				ret=dup2(pipes[1],1);
				if(ret<0)
					printf("dup2 failed\n");
				for(j=0;j<2*count;j++)
					close(pipes[j]);
			}
			else if(i>0 && i<count && count>0){
				//printf("Intermediate child\n");				
				dup2(pipes[(i-1)*2],0);
				dup2(pipes[(i*2)+1],1);
				for(j=0;j<2*count;j++)
					close(pipes[j]);

			}
			else if(i==count && count>0){
				//printf("last Child\n");
				ret=dup2(pipes[(i-1)*2],0);
				if(ret<0)
					printf("dup2 failed\n");
				for(j=0;j<2*count;j++)
					close(pipes[j]);
				
			}
			else {

			}
			
			
			
			if((cmdcount>=2) && (inputflag>0)){
				//printf("entered here to redirect input\n");
				inputfile=fopen(cmdarg[inputflag+1],"r+");

				
				if(inputfile==NULL){
					printf("File cannot be opened\n");
					exit(0);
				}

				fd=fileno(inputfile);
				ret=dup2(fd,0);
				if(ret<0)
					printf("dup2 failed\n");


				close(fd);
				fclose(inputfile);
				for(k=inputflag;k<(cmdcount+1);k++){
				
					if(cmdarg[k+2]==NULL)
						break;
					strcpy(cmdarg[k],cmdarg[k+2]);	
				}
				cmdarg[k]=NULL;
				//printf("dup used for input redirection\n");
				inputflag=0;
				if(outputflag>0)
					outputflag-=2;
			}

			if((cmdcount>=2) && (inputflag_d>0)){
				char *line=malloc(sizeof(char)*100);
				if(line==NULL){
					printf("Memory cannot be allocated\n");
				}				
				//printf("entered here to get input from prompt\n");
				inputfile_d=fopen("temp","w+");
				if(inputfile_d==NULL){
					printf("File cannot be opened\n");
					exit(0);
				}
				scanf(" %[^\n]s",line);
				while(strcmp(line,cmdarg[inputflag_d+1])!=0){
					fputs(line,inputfile_d);
					fputs("\n",inputfile_d);

					scanf(" %[^\n]s",line);
				}
				fclose(inputfile_d);

				strcpy(cmdarg[inputflag_d],"temp");
				cmdarg[inputflag_d+1]=NULL;
				
				inputflag_d=0;
				//printf();
				
				
			}
				
			


			if((cmdcount>=2) && (outputflag>0)){
				//printf("entered here to redirect output\n");
				outputfile=fopen(cmdarg[outputflag+1],"w+");

				//else if(outputflag==2)
				//outputfile=fopen("file.c","a+");
				
				if(outputfile==NULL){
					printf("File cannot be opened\n");
					exit(0);
				}
				fd=fileno(outputfile);
				cmdarg[outputflag]=NULL;
				ret=dup2(fd,1);
				
				if(ret<0)
					printf("dup2 failed\n");
				close(fd);
				fclose(outputfile);
				
				//printf("dup is used for output redirection\n");
				outputflag=0;
			}

			if((cmdcount>=2) && (outputflag_a>0)){
				//printf("entered here to redirect and append output\n");
				appendfile=fopen(cmdarg[outputflag_a+1],"a+");

				if(appendfile==NULL){
					printf("File cannot be opened\n");
					exit(0);
				}
				fd=fileno(appendfile);
				ret=dup2(fd,1);
				if(ret<0)
					printf("dup2 failed\n");
				close(fd);
				fclose(appendfile);
				cmdarg[outputflag_a]=NULL;
				//printf("dup is used to redirect output and also to append output\n");
				outputflag_a=0;

			}

				


			if(strcmp(cmdarg[0],"pwd")==0){
				cwd();
				printf("%s\n",pwd);
				exit(4);
				
			}
			
			else if((strcmp(cmdarg[0],"echo")==0) && (cmdcount==1) && (cmdarg[1][0]=='$')){
				echo_env();
				exit(3);
			}

			else if((strcmp(cmdarg[0],"export")==0) && (cmdcount==1)){
				export_env();
				exit(5);
			}
			
			/*else if((strcmp(cmdarg[0],"history")==0)&&(cmdcount==0)){ //zero arguments
				history();
				exit(6);
			}*/
			
			else if(strcmp(cmdarg[0],"cd")==0){
				exit(2);
			}			

		
			else{
				ret=execvp(cmdarg[0],cmdarg);
				if(ret<0){
					printf("Command not Found\n");
					exit(1);//to terminate child process or else rest of parent would be executed twice
				}
				
			}
			
			
		}
			
			if(count==0){
				if(backgroundflag==1){
					pid_array[job]=pid;
					//printf("background process added at index %d\n",job);
					printf("[%d]\n",job);
					job=(job+1)%6;
					if(job==0)
					job=1;
					
					
				}
				else
				wait(&status);
			}
			
			if((strcmp(cmdarg[0],"export")==0) && (cmdcount==1) && (count==0)){
				export_env();
				
			}
			
			if((strcmp(cmdarg[0],"cd")==0) && (count==0)){
				cd();

			}
			//printf("%d index command is executed\n",i);
			free(cmdarg);
				
		
	}
	if(count>0){
		for(j=0;j<2*count;j++)
			close(pipes[j]);
	
		for(j=0;j<count+1;j++)
			wait(&status);	
	}
	

		

	//printf("parent process is going to end\n");
	
	free(input);
	free(pipearg);
	
	//printf("EXIT EXTERNAL\n");
	
		
	
}
