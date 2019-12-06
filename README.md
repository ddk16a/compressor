# Compressor
### Algorithm Theroy and Tradeoffs
#### Huffman
Huffman builds a trie using the frequncy of characters found in the file to have a code optimized fo the specific file it is compressing

Pors:
- Optimized code for the specific file

Cons:
- Must read the file twice
- Must include the trie in the file so the decompressor can know how to decompress the file

#### LZW
LZW builds a code as the file is read by identify patterns of charachters and assigning a character to represent the pattern.

Pros:
- Only reads the file once
- doensn't need to transmit the code because the decompressor will build it as it reads the file

Cons:
- Doesn't procuce a code optimized for the file


### Instalation
`git clone`

### Test
Navigate to the folder that looks like
```
.
├──	src
├── pom.xml
└── README.md
```
and run `mvn test`. 
Before each of the tests, the files found in the `src\files` folder are copied to the folder `src\originalFiles`. Then, these files are compressed, then decompressed. This results in the files in `sr\files` being overidden with the result of the decompression. After that, the files in the `src\files` folder are compared to the original files in the  `src\originalFiles` folder. The comparison takes into consideration the base name, extension, file contents, and file size. Once the tests are over, the files in `src\originalFiles` are copied back to `src\files`, so that the next files are certaion to have the original files

### Execution
To execute the programs, navigate to the folder `src\main\java` and call
```
java SchubsH <file_name>.. //one or many files
java SchubsL <file_name>.. //one or many files
java SchubsArc <archive_name> <file_name>.. //one or many files. The result is a file <archive_name>.zl
java Deschubs <file_name> //one .ll|.hh|.zl file
```
