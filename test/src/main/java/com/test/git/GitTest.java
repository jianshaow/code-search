package com.test.git;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;

public class GitTest {

	/**
	 * @param args
	 * @throws IOException
	 * @throws GitAPIException
	 */
	public static void main(String[] args) throws IOException, GitAPIException {
		final Git repo = Git
				.open(new File("D:/Devel/repositories/support-tool"));
		printRefs(repo.tagList().call());

		printRefs(repo.branchList().call());

		repo.checkout().setName(repo.tagList().call().get(0).getName()).call();
	}

	private static void printRefs(final List<Ref> refs) {
		for (Ref ref : refs) {
			System.out.println(ref.getName());
		}
	}
}
