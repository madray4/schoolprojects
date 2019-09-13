# Henry Chu
# Hechu
# 110875400

#################### DO NOT CREATE A .data SECTION ####################
#################### DO NOT CREATE A .data SECTION ####################
#################### DO NOT CREATE A .data SECTION ####################

.text
to_lowercase:
	li $t9,0 				#Capital counter
	to_lowercase_start:
	lb $t0, 0($a0)				#Loads character at address in $a0
	beq $t0, $zero,to_lowercase_end		#Once the next value is the null terminating character, we are done iterating through the string
	li $t1, 'A'
	blt $t0, $t1, increment			#If the character loaded in $t0's ascii value is less than 'A', it is not a capital letter and nothing needs to be done
	li $t1, 'Z'
	bgt $t0, $t1, increment			#If the character loaded in $t0's ascii value is greater than 'Z' it is not a capital letter and nothing needs to be done
	addi $t0, $t0, 32			#If $t0 is a capital letter, add 32 to it to turn it into its lower case counterpart
	sb $t0, 0($a0)				#If the letter was a capital letter in str, this replaces that with its lower case counterpart
	addi $t9, $t9, 1			#If a capital letter was changed to lower case, increments counter by 1
	increment:
	addi $a0, $a0, 1			#Increments address of $a0 by 1 byte to have address start at the next character in the string
	j to_lowercase_start			#Restarts loop until string is done being iterated over
	to_lowercase_end:
	move $v0, $t9				#Moves counter value to $v0
	jr $ra

strlen:
	li $t9, 0			#Character counter
	strlen_start:		
	lb $t0, 0($a0)			#Loads character at address in $a0
	beq $t0, $zero, strlen_end 	#Branches to end once string is done itereating
	addi $t9, $t9, 1		#Increments counter by 1 when there is a character in $t0
	addi $a0, $a0, 1		#Increments address of $a0 by 1 byte to have address start at the next character in the string
	j strlen_start			
	strlen_end:
	move $v0, $t9			#Moves counter value to $v0
    	jr $ra
	
count_letters:
	li $t9, 0				#Letter counter
	count_letters_start:
	lb $t0, 0($a0)				#Loads character at address in $a0
	beq $t0, $zero, count_letters_end	#Branches to end once string is done itereating
	addi $a0, $a0, 1			#Increments address of $a0 by 1 byte to have address start at the next character in the string
	li $t1, 'A'
	blt $t0, $t1, count_letters_start	#If ascii value in $t0 is less than 'A' it is not a letter
	li $t1, 'Z'
	ble $t0, $t1, count_letters_is_letter #If ascii value in $t0 is greater than 'A' and less than or equal to 'Z', it is a letter
	li $t1, 'a'
	blt $t0, $t1, count_letters_start	#If ascii value in $t0 is greater than 'Z' and less than 'a', it is not a letter
	li $t1, 'z'
	bgt $t0, $t1, count_letters_start	#If ascii value in $t0 is greater than 'z', it is not a letter
	count_letters_is_letter:				
	addi $t9, $t9, 1			#Increments counter value if $t0 is a letter
	j count_letters_start
	count_letters_end:
	move $v0, $t9
	jr $ra

encode_plaintext:
	li $t8, 0				#Counter for number of encodings done
	
	addi $sp, $sp, -8			#Reserves space to preserve $ra and $a0
	sw $ra, 0($sp)				#Preserves $ra in the stack 
	sw $a0, 4($sp)				#Preserves address of str in stack 
	jal strlen				#Calls function strlen to get string length in $v0
	lw $ra, 0($sp)				#Loads original $ra from before the function call
	lw $a0, 4($sp)				#Loads original starting address of str before the function call
	addi $sp, $sp, 8			#Unreserves the space used to store $ra
	
	li $v1, 1				#Assume there's enough space in ab_text to encode all letter, will be adjusted it that is not the case
	li $t0, 5
	div $a2, $t0				#Divides ab_text_length by 5 to see how many letters can actually be encoded before there is no more space in ab_text
	mflo $t9				#Moves the value to my counter register, $t9
	beq $t9, $zero,	encode_plaintext_no_space	#Jumps to encode_plaintext_no_space if there's not enough space in ab_text even for the end messager marker. Bypasses subtracting 1 from $t9 
	blt $v0, $t9, encode_plaintext_start 	#If there is enough space in ab_text to hold the encoding, jumps to encode_plaintext_start	
	addi $t9, $t9, -1			#$t9 now has how many character can be encoded with the given space, accounting for space needed for end message marker
	encode_plaintext_no_space:
	li $v1, 0
	move $v0, $t9
			
	encode_plaintext_start:
	li $t6, 0				#Counter for iterations through bacon code array
	li $t7, 5				#Counter for iterations through bacon code array
	beq $t8, $v0, encode_plaintext_end	#Once the maximum number of encodings is done, jumps to end
	lb $t0, 0($a0)				#Load character at $a0 into $t0
	beq $t0, $zero, encode_plaintext_end   #If there's enough space to encode all characters, jumps to end when all characters in str have been encoded
	addi $a0, $a0, 1			#Increments address of $a0 by 1 byte to have address start at the next character in the string
	addi $t8, $t8, 1			#Increments counter by 1
	li $t1, 'a'
	blt $t0, $t1, encode_plaintext_not_a_letter	#If $t0 is less than 'a', it is not a letter. Anything greater than or equal to 'a' can be considered a letter
	addi $t0, $t0, -97			#a-z is now 0-25
	li $t1, 5
	mult $t0, $t1				#$t0 multiplied by 5 gives value to increment the starting address by to obtain the proper bacon code
	mflo $t0
	add $t9, $a3, $t0			#t9 now holds starting address of proper bacon code
	j encode_plaintext_save
	encode_plaintext_not_a_letter:
		li $t1, ' '
		bne $t0, $t1, encode_plaintext_exclamation	#Branches if character in $t0 is not a ' '
		li $t1, 26					#A space is the 26th character in bacon code
		li $t2, 5	
		mult $t1, $t2					#Each bacon code is 5 bytes long, so multiplying the 26th character by 5 gives the proper amount to increment by
		mflo $t0
		add $t9, $a3, $t0				#$t9 now holds the starting address of the 26th character in bacon code
		j encode_plaintext_save
	encode_plaintext_exclamation:
		li $t1, '!'
		bne $t0, $t1, encode_plaintext_quotation	#Branches if character in $t0 is not a '!'
		li $t1, 27					#A '!' is the 27th character in bacon code
		li $t2, 5
		mult $t1, $t2
		mflo $t0
		add $t9, $a3, $t0				#$t9 now holds the starting address of the 27th character in bacon code
		j encode_plaintext_save
	encode_plaintext_quotation:				
		li $t1, 39
		bne $t0, $t1, encode_plaintext_comma		#Branches if character in $t0 is not a quotation
		li $t1, 28					#A quotation is the 28th character in bacon code
		li $t2, 5
		mult $t1, $t2
		mflo $t0
		add $t9, $a3, $t0				#$t9 now holds the starting address of the 28th character in bacon code
		j encode_plaintext_save
	encode_plaintext_comma:
		li $t1, ','
		bne $t0, $t1, encode_plaintext_period		#Branches if character in $t0 is not a ','
		li $t1, 29					#A ',' is the 29th character in bacon code
		li $t2, 5
		mult $t1, $t2
		mflo $t0
		add $t9, $a3, $t0				#$t9 now holds the starting address of the 29th character in bacon code
		j encode_plaintext_save
	encode_plaintext_period:
		li $t1, 30					#A '.' is the 30th character in bacon code
		li $t2, 5
		mult $t1, $t2
		mflo $t0
		add $t9, $a3, $t0				#$t9 now holds the stating address of the 30th character in bacon code
	encode_plaintext_save:
		beq $t6, $t7, encode_plaintext_start
		lb $t2, 0($t9)				#Loads bacon code character value at address previously found
		addi $t6, $t6, 1			#Increments counter
		addi $t9, $t9, 1			#Increments address to get next character of bacon code
		sb $t2, 0($a1)
		addi $a1, $a1, 1			#Incremnets ab_text so the next bacon code value placed in doesnt overried bacon code already saved
		j encode_plaintext_save
	encode_plaintext_end:
		blt $a2, $t7, encode_plaintext_final	#If there's less than 5 space in ab_text, cannot add the end message marker so skips to final
		beq $t6, $t7, encode_plaintext_final	#Once 'B' has been saved 5 times, the end message marker is complete
		li $t7, 5
		addi $t6, $t6, 1			#Increments counter
		li $t0, 'B'	
		sb $t0, 0($a1)				#Saves 'B' into $a1
		addi $a1, $a1, 1			#Increments $a1 so all 5 'B's in the end message marker are saved into ab_text
		j encode_plaintext_end	
	encode_plaintext_final:
   	jr $ra
		
encrypt:
	addi $sp, $sp, -8			#Reserves space to preserve $ra and $a0
	sw $ra, 0($sp)				#Preserves $ra in the stack
	sw $a0, 4($sp)				#Preserves address of plaintext in stack
	jal to_lowercase			#Calls function strlen to get string length in $v0
	lw $ra, 0($sp)				#Loads original $ra from before the function call
	lw $a0, 4($sp)				#Loads original starting address of plaintext before the function call
	addi $sp, $sp, 8			#Unreserves the space used to store $ra
	
	lw $t0, 0($sp)				#t0 now contains the starting address of bacon codes
	addi $sp, $sp, -16			#Reserves space for 4 words in the stack
	sw $ra, 0($sp)				#Preserves $ra in the stack pointer
	sw $a1, 4($sp)				#Preserves cipher_text address in stack
	sw $a2, 8($sp)				#Preserves ab_text address in stack
	sw $a3, 12($sp)				#Preserves ab_text_length address in stack
	move $a1, $a2				#Moves address of ab_text to $a2 to use encode_plaintext properly
	move $a2, $a3				#Moves address of ab_text_length to $a2 to use encode_plaintext properly
	move $a3, $t0				#Moves address of bacon $a2 to use encode_plaintext properly
	jal encode_plaintext
	lw $a3, 12($sp)				#Loads original starting address of ab_text_length back to $a3
	lw $a2, 8($sp)				#Loads original starting address of ab_text back to $a2
	lw $a1, 4($sp)				#Loads original starting address of cipher_text back to $a1
	lw $ra, 0($sp)				#Loads original $ra from before the function call
	addi $sp, $sp, 16			#Unreserves the space used to store data on stack
	
	li $t9, 0 				#Counter for number of letters in ciphertext that encode A/B code letters
	li $t0, 5
	mult $v0, $t0
	mflo $v0				#Muliplied $v0 by 5 to get number of letters in the enconded plaintext 
	blt $a3, $t0, encrpyt_transfer_end	#If there isn't enough space in ab_text for even the end message marker, skips everything
	addi $v0, $v0, 5			#If there's at least enough space for the end message marker, adds 5 to account for the end message marker encoding
	
	encrypt_transfer_start:
		bge $t9, $v0, encrpyt_transfer_end	#v0 is equal to the number of letters in ciphertext that can be encoded, once $t9 is equal to $v0. No more encodings can be done
		lb $t0, 0($a1)				#Loads byte value of cipherext
		addi $a1, $a1, 1			#Increments address of $a1 by a byte to get address of next character
		li $t1, 'A'
		blt $t0, $t1, encrypt_transfer_start 	#If ascii value of character is less than 'A' it is not any letter
		li $t1, 'Z'
		ble $t0, $t1, encrypt_capital_letter	#If ascii value of character is between 'A' and 'Z' it is a capital letter
		li $t1, 'a'
		blt $t0, $t1, encrypt_transfer_start	#If ascii value of character is greater than 'Z' and less than 'a', it is not any letter
		li $t1, 'z'
		ble $t0, $t1, encrypt_lower_case_letter#If ascii value of character is between 'a' and 'z' it is a lower case letter
	
	encrypt_capital_letter:
		addi $t9, $t9, 1			#Increments $t9 by 1			
		lb $t1, 0($a2)				#Loads A/B value from ab_text into $t1
		addi $a2, $a2, 1			#Increments $a2 to get the address of the next A/B value
		li $t2, 'A'				#If A/B value is A, the original letter is turned into a lower case 
		bne $t1, $t2, encrypt_transfer_start	#If A/B value is B, and the original character is capital, there is no need to change anything
		addi $t0, $t0, 32			#Changes capital letter in $t0 to corresponding lower case letter in ascii
		addi $a1, $a1, -1			#Decrements address of $a1 so we can change it to lower case
		sb $t0, 0($a1)				#Changes capital letter to lower case letter in cipher text
		addi $a1, $a1, 1			#Increment address of $a1 to preserve loop
		j encrypt_transfer_start
	encrypt_lower_case_letter:
		addi $t9, $t9, 1			#Increments $t9 by 1		
		lb $t1, 0($a2)				#Loads A/B value from ab_text into $t1
		addi $a2, $a2, 1			#Increments $a2 to get the address of the next A/B value
		li $t2, 'B'				#If A/B value is B, the original letter is turned into a capital
		bne $t1, $t2, encrypt_transfer_start	#If A/B value is A, and the original character is lower case, there is no need to change anything
		addi $t0, $t0, -32			#Changes lower case letter in $t0 to corresponding capital letter in ascii
		addi $a1, $a1, -1			#Decrements address of $a1 so we can change it to a capital
		sb $t0, 0($a1)				#Changes lower case letter to capital letter in cipher text
		addi $a1, $a1, 1			#Increment address of $a1 to preserve loop
		j encrypt_transfer_start
	
	encrpyt_transfer_end:
		move $v0, $t9
	jr $ra
	
	
decode_ciphertext:
	addi $sp, $sp, -8		#Reserves space in stack for $ra and $a0
	sw $ra, 0($sp)			#Preserves $ra in stack
	sw $a0, 4($sp)			#Preserves $a0 in stack
	jal count_letters		#Returns number of letters in cipher text in $v0
	lw $a0, 4($sp)			#Restores original $a0
	lw $ra, 0($sp)			#Restores orignal $ra
	addi $sp, $sp, 8		#Unreserves the space used to store data on stack
	
	move $t0, $v0			#Number of letters in cipher text is now in $t0
	li $v0, -1			#Assume number of letters in cipher text is greater than ab_text_length, will adjust later if not so
	bgt $t0, $a2, decode_ciphertext_end	#If number of letters in cipher text is greater than ab_text_length, ends function
	
	li $t0, 0			#Counter for number of letters written to ab_text
	li $t1, 5			#End value for increments of 5
	li $t2, 0			#Counter for number of B's every 5 increments
	decode_ciphertext_start:
		beq $t0, $zero, decode_ciphertext_no_reset	#If counter is at 0, don't check the last 5 bytes to see if they're all B's
		div $t0, $t1					#Sets HI to remainder of counter diveded by 5
		mfhi $t3
		bne $t3, $zero, decode_ciphertext_no_reset	#If remainder in HI is not 0, don't check if the last 5 Bytes saved into ab_text are B's
		move $v0, $t0					#Constantly update $v0 whenever looping
		beq $t2, $t1, decode_ciphertext_end		#If last 5 bytes of ab_text are B's end 
		li $t2, 0					#Else reset counter for B's
	decode_ciphertext_no_reset:
		lb $t3 0($a0)					#Loads byte value of $a0 
		addi $a0, $a0, 1				#Increments $a0 by 1 so the next loop has the proper address of the next byte
		li $t4, 'A'
		blt $t3, $t4, decode_ciphertext_start		#Anything less than 'A' is not a letter, reset loop
		li $t4, 'Z'
		ble $t3, $t4, decode_ciphertext_capital	#Anything between 'A' and 'Z' is a capital 
		li $t4, 'a'
		blt $t3, $t4, decode_ciphertext_start		#Anything greater than 'Z' and less than 'a' is not a letter, reset loop
		li $t4, 'z'
		ble $t3, $t4, decode_ciptertext_lower_case	#Anything greater than 'z' is not a letter
		j decode_ciphertext_start
	decode_ciphertext_capital:
		addi $t0, $t0, 1				#Increments counter by 1 when we know its some kind of letter
		addi $t2, $t2, 1				#Increments B counter since we know its a capital letter
		li $t4, 'B'
		sb $t4, 0($a1)					#Saves 'B' into $a1 because the character being checked in cipher_text is capital
		addi $a1, $a1, 1				#Increments ab_text by 1 byte
		j decode_ciphertext_start
	decode_ciptertext_lower_case:
		addi $t0, $t0, 1				#Increments counter by 1 when we know its some kind of letter
		li $t4, 'A'			
		sb $t4, 0($a1)					#Saves 'A' into $a1 because the character being checked in cipher_text is lower case
		addi $a1, $a1, 1				#Increments ab_text by 1 byte				
		j decode_ciphertext_start
	decode_ciphertext_end:
	jr $ra
	
decrypt:
	lw $t0, 0($sp)				#t0 now contains the starting address of bacon codes
	addi $sp, $sp, -16			#Reserves space for 4 words in the stack
	sw $ra, 0($sp)				#Preserves $ra in the stack pointer
	sw $a1, 4($sp)				#Preserves cipher_text address in stack
	sw $a2, 8($sp)				#Preserves ab_text address in stack
	sw $a3, 12($sp)				#Preserves ab_text_length address in stack
	move $a1, $a2				#Moves address of ab_text to $a2 to use encode_plaintext properly
	move $a2, $a3				#Moves address of ab_text_length to $a2 to use encode_plaintext properly
	move $a3, $t0				#Moves address of bacon $a2 to use encode_plaintext properly
	jal decode_ciphertext
	lw $a3, 12($sp)				#Loads original starting address of ab_text_length back to $a3
	lw $a2, 8($sp)				#Loads original starting address of ab_text back to $a2
	lw $a1, 4($sp)				#Loads original starting address of cipher_text back to $a1
	lw $ra, 0($sp)				#Loads original $ra from before the function call
	addi $sp, $sp, 16			#Unreserves the space used to store data on stack
	
	bgt $v0, $zero, decrypt_not_zero	#If decode_ciphertext returns -1, jumps to end and doesn't change anything and returns to original function
	jr $ra
	decrypt_not_zero:
	addi $v0, $v0, -5			#Subtract 5 to account for the end of message marker
	li $t1, 5				#Used as counter end value as well as dividing $v0 from decode_ciphertext
	div $v0, $t1				#Divide number of decoded letters by 5 to get number of letters that will end up in the decrypted message
	mflo $v0				#Set $v0 to result of div to get proper value in $v0
	
	li $t8, 0				#$t8 is used to check the binary value of every 5 increments of A/B text, A=0, B=1
	li $t9, 0				#Counter for increments of 5 
			
	decrypt_start:
		sll $t8, $t8, 1					#Shifts $t6 to the left by 1 so next binary value can be added
		addi $t9, $t9, 1				#Increment counter
		lb $t2, 0($a2)					#Loads A or B from ab_text
		addi $a2, $a2, 1				#Increments $a2 by 1 byte
		li $t3, 'B'
		bne $t2, $t3, decrypt_A			#If value is A will branch to avoid adding to the B counter
		addi $t8, $t8, 1				#Adds 1 to $t6 to account for B being equal to 1
		decrypt_A:
		blt $t9, $t1, decrypt_start			#If $t9 is not at 5, doesn't check values 
		li $t3, 31
		beq $t8, $t3, decrypt_final			#If $t8 is at 5, this indicates the end of message marker has been found
			li $t3, 25
			bgt $t8, $t3, decrypt_not_letter		#Any value of $t8 greater than 25 is not a letter
			addi $t8, $t8, 65				#Difference between binary code of appropriate bacon code and ascii value is 65
			sb $t8, 0($a1)					#Stores decrypted letter into plaintext
			j decrypt_end
		decrypt_not_letter:
			li $t3, 26					#26 in binary bacon code is a space
			bne $t8, $t3, decrypt_exclamation
			li $t3, ' '	
			sb $t3, 0($a1)					#Stores space in plaintext
			j decrypt_end
		decrypt_exclamation:
			li $t3, 27					#27 in binary bacon code is an exclamation 
			bne $t8, $t3, decrypt_quotation
			li $t3, '!'
			sb $t3, 0($a1)					#Stores exclamation in plaintext
			j decrypt_end
		decrypt_quotation:
			li $t3, 28					#28 in binary bacon code is a quotation
			bne $t8, $t3, decrypt_comma
			li $t3, 39
			sb $t3, 0($a1)					#Stores quotation in plaintext
			j decrypt_end
		decrypt_comma:
			li $t3, 29					#29 in binary bacon code is a comma
			bne $t8, $t3, decrypt_period
			li $t3, ','
			sb $t3, 0($a1)					#Stores comma in plaintext
			j decrypt_end
		decrypt_period:					#Must be a period if it is none of the previously checked character
			li $t3, '.'
			sb $t3, 0($a1)				#Stores period in plaintext
		decrypt_end:
			addi $a1, $a1, 1				#Increments plaintext so we don't write over values already placed in
			li $t8, 0					#Reset binary counter
			li $t9, 0					#Resets increment counter
			j decrypt_start
	decrypt_final:
		li $t2, '\0'						#End of message marker '0'
		sb $t2, 0($a1)						#Saves '0' into plaintext
	jr $ra

#################### DO NOT CREATE A .data SECTION ####################
#################### DO NOT CREATE A .data SECTION ####################
#################### DO NOT CREATE A .data SECTION ####################
