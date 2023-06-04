#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "./code.c"

// �û�ʵ�ֵĺ���
int add(int a, int b);

// ��������
struct TestCase {
    int a;
    int b;
    int expected;
};

// ��һ���ַ����н�������������
void parse_test_case(const char *line, int line_num, struct TestCase *test_case) {
    char *input_str = strtok((char *)line, ";");
    char *output_str = strtok(NULL, ";");
    if (input_str == NULL || output_str == NULL) {
        fprintf(stderr, "Error parsing test case on line %d\n", line_num);
        exit(1);
    }
    sscanf(input_str, "%d,%d", &test_case->a, &test_case->b);
    sscanf(output_str, "%d", &test_case->expected);
}

int main() {
    FILE *fp = fopen("./test.txt", "r");
    if (fp == NULL) {
        fprintf(stderr, "Error opening test file\n");
        return 1;
    }
    char line[1024];
    int line_num = 0;
    while (fgets(line, sizeof(line), fp)) {
        line_num++;
        // ȥ����ĩ�Ļ��з�
        size_t len = strlen(line);
        if (len > 0 && line[len - 1] == '\n') {
            line[len - 1] = '\0';
        }
        // ������������
        struct TestCase test_case;
        parse_test_case(line, line_num, &test_case);
        // ���в�������
        int result = add(test_case.a, test_case.b);
        if (result != test_case.expected) {
            fprintf(stderr, "%d:%d,%d:%d:%d\n",
                    line_num, test_case.a, test_case.b, result, test_case.expected);
        }
    }
    fclose(fp);
    return 0;
}
