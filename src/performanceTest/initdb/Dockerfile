FROM ubuntu:22.04

# 패키지 설치 및 스크립트 복사
RUN apt-get update -y 
RUN  apt-get install -y openssh-client mysql-client
# RUN chmod 600 /bastion.pem

# 스크립트 실행
CMD ["/bin/bash", "/initdb.sh"]