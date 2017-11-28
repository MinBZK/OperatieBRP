FROM nginx

COPY dist /usr/share/nginx/html
COPY nginx/run.sh /run.sh
COPY nginx/frontend.conf.template /etc/nginx/conf.d/frontend.conf.template

CMD ["/bin/sh", "/run.sh"]
