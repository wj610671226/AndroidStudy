#include<stdlib.h>
#include<iostream.h>
#include<stdio.h>
#include<conio.h>
#include<winsock2.h>
#include<windows.h>

#pragma comment(lib,"ws2_32.lib")

typedef unsigned char uint8_t;        ///< 8 bit unsigned int

typedef signed char int8_t;          ///< 8 bit signed int

typedef unsigned short uint16_t;        ///< 16 bit unsigned int

typedef signed short int16_t;          ///< 16 bit signed int

typedef unsigned long uint32_t;       ///< 32 bit unsigned int


#define CRC_POLYNOM (0x8408)
#define CRC_PRESET	(0xFFFF)

static SOCKET gs_ClientSocket;
static uint8_t NoPersonAccessData[] = {0x14, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
//12 34 56 78 01 00 00 00 01 01 09 0A 87 65 43 21
static uint8_t gs_SocketReceiveBuffer[1024];
static uint8_t gs_SocketSendBuffer[1024];
static uint8_t gs_ConAddr = 0x00;
static uint8_t gs_msgBuffer[1024];
static uint8_t gs_tempBuffer[256];


uint16_t CRC16(char* pdata,int count)
{
	uint16_t crc = CRC_PRESET;
	for (int i=0; i< count; i++)  /*cnt : �����CRC����ݳ��ȣ�����CRC��*/
	{
		crc ^= pdata[i];
		for (int j=0; j<8; j++)
		{
			if (crc & 0x0001)
				crc = (crc >> 1) ^ CRC_POLYNOM;
			else
				crc = (crc>>1);
		}
	}
	return crc;
}


BOOL ConnectSocket(char* IpAddr,int serverPort)
{
	int err;
	WORD versionRequired;
	WSADATA wsaData;
	versionRequired=0x0002;
	err=WSAStartup(versionRequired,&wsaData);//Э���İ汾��Ϣ

	if (err)
	{
		return FALSE;//����
	}

	gs_ClientSocket = socket(AF_INET,SOCK_STREAM,0);
	SOCKADDR_IN clientsock_in;
	clientsock_in.sin_addr.S_un.S_addr = inet_addr((const char*)IpAddr);
	clientsock_in.sin_family = AF_INET;
	clientsock_in.sin_port = htons(serverPort);

	if(connect(gs_ClientSocket,(SOCKADDR*)&clientsock_in,sizeof(SOCKADDR)) < 0){
		return FALSE;
	}
	return TRUE;
}

void CloseSocket(void)
{
	closesocket(gs_ClientSocket);
	WSACleanup();
}

void SendSocketData(char* pData,uint16_t len)
{
	send(gs_ClientSocket,pData,len,0);
}


void RFID_Acknowledge(void)
{
	uint16_t length = 0;
	uint16_t crc16 = 0;

	memset(gs_SocketSendBuffer,0,sizeof(gs_SocketSendBuffer));
	gs_SocketSendBuffer[length++] = 0x05;
	gs_SocketSendBuffer[length++] = gs_ConAddr;
	gs_SocketSendBuffer[length++] = 0x41;
	crc16 = CRC16((char*)gs_SocketSendBuffer,length);
	gs_SocketSendBuffer[length++] = crc16 & 0xFF;
	gs_SocketSendBuffer[length++] = (crc16 >> 8 ) & 0xFF;
	SendSocketData((char*)gs_SocketSendBuffer,length);
}

void RFID_SendReadChannelCMD(void)
{
	uint16_t length = 0;
	uint16_t crc16 = 0;

	memset(gs_SocketSendBuffer,0,sizeof(gs_SocketSendBuffer));
	gs_SocketSendBuffer[length++] = 0x05;
	gs_SocketSendBuffer[length++] = gs_ConAddr;
	gs_SocketSendBuffer[length++] = 0x43;
	crc16 = CRC16((char*)gs_SocketSendBuffer,length);
	gs_SocketSendBuffer[length++] = crc16 & 0xFF;
	gs_SocketSendBuffer[length++] = (crc16 >> 8 ) & 0xFF;
	SendSocketData((char*)gs_SocketSendBuffer,length);
}

//ctrlType,1:close  2:open  3:query
void RFID_RedrayControlCMD(uint8_t ctrlType)
{
	uint16_t length = 0;
	uint16_t crc16 = 0;

	memset(gs_SocketSendBuffer,0,sizeof(gs_SocketSendBuffer));
	gs_SocketSendBuffer[length++] = 0x06;
	gs_SocketSendBuffer[length++] = gs_ConAddr;
	gs_SocketSendBuffer[length++] = 0x49;
	gs_SocketSendBuffer[length++] = ctrlType;
	crc16 = CRC16((char*)gs_SocketSendBuffer,length);
	gs_SocketSendBuffer[length++] = crc16 & 0xFF;
	gs_SocketSendBuffer[length++] = (crc16 >> 8 ) & 0xFF;
	SendSocketData((char*)gs_SocketSendBuffer,length);

	Sleep(200);
	memset(gs_tempBuffer,0,sizeof(gs_tempBuffer));
	recv(gs_ClientSocket, (char*)gs_tempBuffer, sizeof(gs_tempBuffer), 0);
}

//getSet,0:Get  1:Set
void RFID_SetControlAddrCMD(uint8_t getSet,uint8_t addr)
{
	uint16_t length = 0;
	uint16_t crc16 = 0;
	
	gs_ConAddr = addr;
	memset(gs_SocketSendBuffer,0,sizeof(gs_SocketSendBuffer));
	gs_SocketSendBuffer[length++] = 0x07;
	gs_SocketSendBuffer[length++] = 0x00;
	gs_SocketSendBuffer[length++] = 0x4B;
	gs_SocketSendBuffer[length++] = getSet;
	gs_SocketSendBuffer[length++] = addr;
	crc16 = CRC16((char*)gs_SocketSendBuffer,length);
	gs_SocketSendBuffer[length++] = crc16 & 0xFF;
	gs_SocketSendBuffer[length++] = (crc16 >> 8 ) & 0xFF;
	SendSocketData((char*)gs_SocketSendBuffer,length);
	
	Sleep(200);
	memset(gs_tempBuffer,0,sizeof(gs_tempBuffer));
	recv(gs_ClientSocket, (char*)gs_tempBuffer, sizeof(gs_tempBuffer), 0);
}

//getSet,0:Get  1:Set
void RFID_LedBeeCtrlCMD(void)
{
	uint16_t length = 0;
	uint16_t crc16 = 0;

	memset(gs_SocketSendBuffer,0,sizeof(gs_SocketSendBuffer));
	gs_SocketSendBuffer[length++] = 0x0B;
	gs_SocketSendBuffer[length++] = 0x00;
	gs_SocketSendBuffer[length++] = 0x42;
	gs_SocketSendBuffer[length++] = 1;
	gs_SocketSendBuffer[length++] = 10;
	gs_SocketSendBuffer[length++] = 1;
	gs_SocketSendBuffer[length++] = 1;
	gs_SocketSendBuffer[length++] = 10;
	gs_SocketSendBuffer[length++] = 1;
	crc16 = CRC16((char*)gs_SocketSendBuffer,length);
	gs_SocketSendBuffer[length++] = crc16 & 0xFF;
	gs_SocketSendBuffer[length++] = (crc16 >> 8 ) & 0xFF;
	SendSocketData((char*)gs_SocketSendBuffer,length);

	Sleep(50);
	memset(gs_tempBuffer,0,sizeof(gs_tempBuffer));
	recv(gs_ClientSocket, (char*)gs_tempBuffer, sizeof(gs_tempBuffer), 0);
}

void RFID_ClearAllBackgroundDateCMD(void)
{
	uint16_t length = 0;
	uint16_t crc16 = 0;

	memset(gs_SocketSendBuffer,0,sizeof(gs_SocketSendBuffer));
	gs_SocketSendBuffer[length++] = 0x05;
	gs_SocketSendBuffer[length++] = 0x00;
	gs_SocketSendBuffer[length++] = 0x54;
	crc16 = CRC16((char*)gs_SocketSendBuffer,length);
	gs_SocketSendBuffer[length++] = crc16 & 0xFF;
	gs_SocketSendBuffer[length++] = (crc16 >> 8 ) & 0xFF;
	SendSocketData((char*)gs_SocketSendBuffer,length);

	Sleep(200);
	memset(gs_tempBuffer,0,sizeof(gs_tempBuffer));
	recv(gs_ClientSocket, (char*)gs_tempBuffer, sizeof(gs_tempBuffer), 0);
}

void RFID_ClearChannelDataCMD(void)
{
	uint16_t length = 0;
	uint16_t crc16 = 0;

	memset(gs_SocketSendBuffer,0,sizeof(gs_SocketSendBuffer));
	gs_SocketSendBuffer[length++] = 0x05;
	gs_SocketSendBuffer[length++] = 0x00;
	gs_SocketSendBuffer[length++] = 0x44;
	crc16 = CRC16((char*)gs_SocketSendBuffer,length);
	gs_SocketSendBuffer[length++] = crc16 & 0xFF;
	gs_SocketSendBuffer[length++] = (crc16 >> 8 ) & 0xFF;
	SendSocketData((char*)gs_SocketSendBuffer,length);

	Sleep(200);
	memset(gs_tempBuffer,0,sizeof(gs_tempBuffer));
	recv(gs_ClientSocket, (char*)gs_tempBuffer, sizeof(gs_tempBuffer), 0);
}


int RFID_ReceiveSocketData(int* readLen)
{
	int receiveLen = 0;
	uint16_t dataLength = 0;
	BOOL retCode = FALSE;

	while(1)
	{
		receiveLen = 0;
		memset(gs_tempBuffer,0,sizeof(gs_tempBuffer));
		receiveLen = recv(gs_ClientSocket, (char*)gs_tempBuffer, sizeof(gs_tempBuffer), 0);
		if(receiveLen==0) 
		{
			*readLen = dataLength;
			RFID_Acknowledge();
			return 0;
		}

		if(receiveLen == -1)//error
		{
			return -2;
		}
		memcpy(gs_SocketReceiveBuffer + dataLength,gs_tempBuffer,receiveLen);
		dataLength += receiveLen;
		
		if(dataLength < gs_SocketReceiveBuffer[0])
		{
			//printf("AAA %d,%d    ",retval,gs_SocketReceiveBuffer[0]);
			continue;
		}
		else
		{
			//printf("BBB %d,%d    ",retval,gs_SocketReceiveBuffer[0]);
			*readLen = dataLength;
			RFID_Acknowledge();
			return 0;
		}
	}
}

long RFID_GetChannelMessage(uint8_t* msgBuffer, uint8_t* msgLength , uint8_t *msgType)
{
	int receiveLen = 0;
	uint16_t length = 0;
	uint16_t crc16 = 0;
	uint8_t* pSocktBuffer = gs_SocketReceiveBuffer;
	int retCode = 0;
	
	if(msgBuffer == NULL || msgLength == NULL || msgType == NULL)
	{
		return -1;
	}

	*msgLength = 0;
	*msgType = 0xFF;

	retCode = RFID_ReceiveSocketData(&receiveLen);
	if(retCode == 0)
	{
		uint8_t msgLen = pSocktBuffer[0];
		if(receiveLen != msgLen)
		{
			printf("x");
			return -1;
		}
		if(msgLen == 0x14)//常规响应
		{
			uint8_t conAddr = pSocktBuffer[1];
			uint8_t status = pSocktBuffer[2];
			if(status == 0x00)
			{
				*msgType = 0;
				*msgLength = msgLen - 5;
				memcpy(msgBuffer,&pSocktBuffer[3],*msgLength);
				gs_ConAddr = conAddr;
				return 0;
			}
			else 
			{
				printf("a");
				return -1;
			}
		}
		else if(msgLen == 0x13)//统计消息
		{
			uint8_t conAddr = pSocktBuffer[1];
			uint8_t status = pSocktBuffer[2];
			if(conAddr == gs_ConAddr && status == 0x01)
			{
				*msgType = 1;
				*msgLength = msgLen - 5;
				memcpy(msgBuffer,&pSocktBuffer[3],*msgLength);
				return 0;
			}
			else  if(conAddr == gs_ConAddr && status == 0x02)
			{
				*msgType = 2;
				*msgLength = msgLen - 5;
				memcpy(msgBuffer,&pSocktBuffer[3],*msgLength);
				return 0;
			}
			else
			{
				printf("b");
				return -1;
			}
		}		
		else if(msgLen > 0x14)
		{
			uint8_t conAddr = pSocktBuffer[1];
			uint8_t status = pSocktBuffer[2];
			if(conAddr == gs_ConAddr && status == 0x00)
			{
				*msgType = 0;
				*msgLength = msgLen - 5;
				memcpy(msgBuffer,&pSocktBuffer[3],*msgLength);
				return 0;
			}
			else if(conAddr == gs_ConAddr && status == 0x02)
			{
				*msgType = 2;
				*msgLength = msgLen - 5;
				memcpy(msgBuffer,&pSocktBuffer[3],*msgLength);
				return 0;
			}
			else 
			{
				printf("c");
				return -1;
			}
		}		
		else if(msgLen == 0x0B)
		{
			uint8_t conAddr = pSocktBuffer[1];
			uint8_t status = pSocktBuffer[2];
			if(conAddr == gs_ConAddr && status == 0x03)
			{
				*msgType = 3;
				*msgLength = msgLen - 5;
				memcpy(msgBuffer,&pSocktBuffer[3],*msgLength);
				return 0;
			}
			else if(conAddr == gs_ConAddr && status == 0x04)
			{
				*msgType = 4;
				*msgLength = msgLen - 5;
				memcpy(msgBuffer,&pSocktBuffer[3],*msgLength);
				return 0;
			}
			else 
			{
				printf("d");
				return -1;
			}
		}		
		else
		{
			//printf("\n5RL=%d\n",receiveLen);
			return -1;
		}
	}
	else
	{
		return retCode;
	}
}

void RFID_ReopenSocket()
{
	BOOL retCode;

	CloseSocket();
	
	retCode = ConnectSocket("10.10.0.111",8080);
	if(retCode == TRUE)
	{
		printf("Socket open OK!\n");
	}
	else
	{
		printf("Socket open failed!\n");
	}
}

unsigned long gs_count = 0;
unsigned long gs_Cardcount = 0;
void main(void)
{
	char *smsg="X";
	BOOL retCode;
	uint8_t lastUID[17];

	retCode = ConnectSocket("10.10.0.111",8080);	
	if(retCode == TRUE)
	{
		printf("Socket open OK!\n");
	}
	else
	{
		printf("Socket open failed!\n");
		return;
	}

	uint8_t msgLength = 0;
	uint8_t msgType = 0;
	long cmdRet = 0;
	uint8_t UID[17];
	memset(lastUID,0,sizeof(lastUID));

	RFID_SetControlAddrCMD(1,0);
	Sleep(500);
	RFID_RedrayControlCMD(1);
	RFID_ClearChannelDataCMD();
	RFID_ClearAllBackgroundDateCMD();

	while(1)
	{
		memset(gs_SocketReceiveBuffer,0,sizeof(gs_SocketReceiveBuffer));
		RFID_SendReadChannelCMD();
		cmdRet = RFID_GetChannelMessage(gs_msgBuffer,&msgLength,&msgType);
		if(cmdRet == -2)
		{
			RFID_ReopenSocket();
			continue;
		}

		gs_count++;
		if(gs_count % 20 == 0)
			printf("%d ",gs_Cardcount);

		
		if(cmdRet == 0 && msgType == 0)////常规消息
		{
			uint8_t inOutFlag;
			uint8_t InOrOut[20];
			uint8_t DateTime[30];

			memset(UID,0,sizeof(UID));
			memset(InOrOut,0,sizeof(InOrOut));
			memset(DateTime,0,sizeof(DateTime));

			sprintf((char*)UID,"%02X%02X%02X%02X%02X%02X%02X%02X",
				gs_msgBuffer[0],gs_msgBuffer[1],gs_msgBuffer[2],gs_msgBuffer[3],
				gs_msgBuffer[4],gs_msgBuffer[5],gs_msgBuffer[6],gs_msgBuffer[7]);

			inOutFlag = gs_msgBuffer[8];
			switch(inOutFlag & 0x0F)
			{
				case 1:
					strcpy((char*)InOrOut,"正向");
					break;
				case 2: 
					strcpy((char*)InOrOut,"反向");
					break;
				case 3: 
					strcpy((char*)InOrOut,"方向不定");
					break;
				case 4: 
					strcpy((char*)InOrOut,"红外已经关闭");
					break;
				default:
					break;
			}

			sprintf((char*)DateTime,"%04d-%02d-%02d-%02d:%02d:%02d",gs_msgBuffer[9] + 2000,gs_msgBuffer[10],gs_msgBuffer[11],gs_msgBuffer[12],gs_msgBuffer[13],gs_msgBuffer[14]);

			if(strcmp((char*)UID,"0000000000000000") == 0){
				if(strcmp((char*)lastUID,(char*)UID) != 0)
				{
					printf("\n%s",(char*)DateTime);
					printf("  无人");
					printf("\n");
					
					strcpy((char*)lastUID,(char*)UID);
				}
			}
			else if(strcmp((char*)UID,"FFFFFFFFFFFFFFFF") == 0)
			{
				printf("\n%s",(char*)DateTime);
				printf("  ");
				printf((char*)UID);
				printf("  ");
				printf((char*)InOrOut);
				printf("\n");
			}
			else if (inOutFlag == 4)
			{
				gs_Cardcount++;
				printf("\n%s",(char*)DateTime);
				printf("  ");
				printf((char*)UID);
				printf("  ");
				printf("红外已经关闭");
				printf("\n");
				RFID_LedBeeCtrlCMD();
			}
			else
			{
				gs_Cardcount++;
				printf("\n%s",(char*)DateTime);
				printf("  ");
				printf((char*)UID);
				printf("  ");
				printf((char*)InOrOut);
				printf("\n");
				RFID_LedBeeCtrlCMD();
			}

			if((inOutFlag & 0xF0) == 0x30)//有标签数据
			{

			}

			memset(gs_SocketReceiveBuffer,0,sizeof(gs_SocketReceiveBuffer));
			//RFID_Acknowledge();
		}
		else if(cmdRet == 0 && msgType == 1)//统计信息
		{
			uint8_t DateTime[30];

			memset(DateTime,0,sizeof(DateTime));
			unsigned short EnterCount;
			unsigned short LeaveCount;
			unsigned short UnsureCount;
			unsigned short CardNum;
	
			EnterCount = gs_msgBuffer[0] | (gs_msgBuffer[1] << 8);
			LeaveCount = gs_msgBuffer[2] | (gs_msgBuffer[3] << 8);
			UnsureCount = gs_msgBuffer[4] | (gs_msgBuffer[5] << 8);
			CardNum = gs_msgBuffer[6] | (gs_msgBuffer[7] << 8);

			sprintf((char*)DateTime,"%04d-%02d-%02d-%02d:%02d:%02d",gs_msgBuffer[8] + 2000,gs_msgBuffer[9],gs_msgBuffer[10],gs_msgBuffer[11],gs_msgBuffer[12],gs_msgBuffer[13]);
	
			printf("\n%s",(char*)DateTime);
			printf("  ");
			printf("%d%s",EnterCount,"人正向");
			printf("  ");
			printf("%d%s",LeaveCount,"人反向");
			printf("  ");
			printf("%d%s",UnsureCount,"方向不定");
			printf("\n");
			printf("共同检测:\t");
			printf("%d%s",CardNum,"张卡片");
			printf("\n");

			memset(gs_SocketReceiveBuffer,0,sizeof(gs_SocketReceiveBuffer));
			//RFID_Acknowledge();
		}
		else if(cmdRet == 0 && msgType == 2)//卡片超时
		{
			uint8_t DateTime[30];

			memset(UID,0,sizeof(UID));
			memset(DateTime,0,sizeof(DateTime));
			
			sprintf((char*)UID,"%02X%02X%02X%02X%02X%02X%02X%02X",
				gs_msgBuffer[0],gs_msgBuffer[1],gs_msgBuffer[2],gs_msgBuffer[3],
				gs_msgBuffer[4],gs_msgBuffer[5],gs_msgBuffer[6],gs_msgBuffer[7]);

			sprintf((char*)DateTime,"%04d-%02d-%02d-%02d:%02d:%02d",
				gs_msgBuffer[8] + 2000,gs_msgBuffer[9],gs_msgBuffer[10],
				gs_msgBuffer[11],gs_msgBuffer[12],gs_msgBuffer[13]);
			

			printf("\n%s",(char*)DateTime);
			printf("  ");
			printf((char*)UID);
			printf("超时");
			printf("\n");
			if((gs_msgBuffer[13]&0x0F) ==0x03)//有卡片数据
			{
				
			}

			memset(gs_SocketReceiveBuffer,0,sizeof(gs_SocketReceiveBuffer));
			//RFID_Acknowledge();
		}
		else if(cmdRet == 0 && msgType == 3)//ͨ通过超时
		{
			uint8_t DateTime[30];

			memset(DateTime,0,sizeof(DateTime));

			sprintf((char*)DateTime,"%04d-%02d-%02d-%02d:%02d:%02d",
				gs_msgBuffer[0] + 2000,gs_msgBuffer[1],gs_msgBuffer[2],
				gs_msgBuffer[3],gs_msgBuffer[4],gs_msgBuffer[5]);
			
			printf("\n%s",(char*)DateTime);
			printf("通过超时");
			printf("\n");

			memset(gs_SocketReceiveBuffer,0,sizeof(gs_SocketReceiveBuffer));
			//RFID_Acknowledge();
		}
		else if(cmdRet == 0 && msgType == 4)//红外探测报警
		{
			uint8_t DateTime[30];

			memset(DateTime,0,sizeof(DateTime));

			sprintf((char*)DateTime,"%04d-%02d-%02d-%02d:%02d:%02d",
				gs_msgBuffer[0] + 2000,gs_msgBuffer[1],gs_msgBuffer[2],
				gs_msgBuffer[3],gs_msgBuffer[4],gs_msgBuffer[5]);
			
			printf("\n%s",(char*)DateTime);
			printf("红外探测报警");
			printf("\n");

			memset(gs_SocketReceiveBuffer,0,sizeof(gs_SocketReceiveBuffer));
			//RFID_Acknowledge();
		}
	}
	CloseSocket();
}

